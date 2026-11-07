abstract class RegexGuardTask : DefaultTask() {
    @get:Input
    abstract val patterns: MapProperty<String, String>

    @get:InputFiles
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val sourceFiles: ConfigurableFileCollection

    @get:OutputFile
    abstract val reportFile: RegularFileProperty

    @TaskAction
    fun scan() {
        val rules = patterns.get().map { (ruleName, pattern) -> ruleName to Regex(pattern) }
        val violations = mutableListOf<String>()

        for (file in sourceFiles) {
            file.useLines { lines ->
                lines.forEachIndexed { index, line ->
                    val hit = rules.firstOrNull { (_, regex) -> regex.containsMatchIn(line) }
                    if (hit != null) {
                        violations.add("${file.path}:${index + 1}: ${hit.first}")
                    }
                }
            }
        }

        reportFile.get().asFile.writeText(
            if (violations.isEmpty()) "OK" else violations.joinToString("\n"),
        )

        if (violations.isNotEmpty()) {
            val message = violations.joinToString("\n")
            println(message)
            throw GradleException("$name found violations:\n$message")
        }
    }
}

tasks.register<RegexGuardTask>("motionGuard") {
    patterns.set(
        mapOf(
            "tween" to """\btween\s*(<[^>]*>)?\s*\(""",
            "linear-easing" to """\bLinearEasing\b""",
        ),
    )
    sourceFiles.setFrom(
        fileTree("app/src/main") {
            include("**/*.kt")
            exclude("**/core/designsystem/tokens/**", "**/core/designsystem/motion/**")
        },
    )
    reportFile.set(layout.buildDirectory.file("reports/motionGuard/report.txt"))
}

tasks.register<RegexGuardTask>("domainGuard") {
    patterns.set(mapOf("android-import" to """^\s*import\s+android\."""))
    sourceFiles.setFrom(
        fileTree("app/src/main") {
            include("**/domain/**/*.kt")
        },
    )
    reportFile.set(layout.buildDirectory.file("reports/domainGuard/report.txt"))
}

tasks.register<RegexGuardTask>("stringGuard") {
    patterns.set(
        mapOf(
            "text-literal" to """\b(Basic)?Text\(\s*(text\s*=\s*)?"[^"]""",
            "content-description-literal" to """\bcontentDescription\s*=\s*"[^"]""",
        ),
    )
    sourceFiles.setFrom(
        fileTree("app/src/main") {
            include("**/*.kt")
        },
    )
    reportFile.set(layout.buildDirectory.file("reports/stringGuard/report.txt"))
}

tasks.register<RegexGuardTask>("forbiddenDepGuard") {
    patterns.set(
        mapOf(
            "forbidden-dependency" to
                """com\.google\.android\.gms|com\.google\.firebase|com\.google\.android\.play""" +
                """|com\.android\.billingclient|com\.google\.mlkit|com\.crashlytics|io\.fabric""" +
                """|com\.facebook\.android|com\.appsflyer|com\.adjust|com\.mixpanel|com\.amplitude""" +
                """|com\.flurry|io\.sentry""",
        ),
    )
    sourceFiles.setFrom(
        files(
            "gradle/libs.versions.toml",
            "build.gradle.kts",
            "settings.gradle.kts",
            "app/build.gradle.kts",
        ),
    )
    reportFile.set(layout.buildDirectory.file("reports/forbiddenDepGuard/report.txt"))
}
