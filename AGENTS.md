# AGENTS.md: Melodia

Always-on rules for the coding agent. Read on every task. Task prompts say "Follow AGENTS.md" and do not repeat these rules.

**Last updated:** 2026-11-05 · **Status:** READY (v1)
**Legend:** ✅ confirmed by owner · 🟡 assumed (owner may veto) · ⚠️ external fact not verified
**Spec (what to build):** `docs/MELODIA_PROJECT_MEMORY.md` · **Prompt format (how prompts work):** `docs/PROMPT_STRUCTURE.md`
**Rules file size limit of the agent tool:** TBD ⚠️

---

## 1. Workflow 🟡

1. Read the whole prompt file. Read the memory-file sections it lists before writing code.
2. Write a short plan (files, order). Proceed unless a stop trigger from section 2 fires.
3. Run the prompt's recon commands first. Save the output with `tee` to `docs/reports/<ID>_recon.txt`, then make the empty marker commit. It must be the oldest commit on the branch.
4. Implement in the prompt's order: dependencies first, UI last.
5. Run the verify command. It must pass before you report done. Verify command: `./gradlew checkAll` (created in prompt P00; before P00 exists, skip this step). 🟡 D136
6. Write `docs/reports/<ID>_report.md` with exactly the headings the prompt gives. A report in another format is incomplete.
7. Run long commands and tests with a timeout. Tests with endless loops (tickers, collectors) use a child scope and a timeout.
8. The owner's login shell is fish. Run every shell command through bash (`bash -c '...'`). Do not rely on fish syntax or heredocs. 🟡 D126

## 2. Stop-and-report triggers 🟡

Stop editing and report (list each under "Deviations from the prompt") when:

- the prompt conflicts with the memory file
- a fact marked verified proves wrong
- a file to create already exists
- a symbol the prompt assumes is missing or has a different signature, and the prompt gives no STOP or "add the smallest method" rule for it
- you need a dependency that is not listed in memory file section 3
- an ambiguity would change the architecture
- a permission or system behavior differs from the prompt
- a change would put something into core that Play Store policy or the F-Droid inclusion policy forbids (memory file D8, D9)

Never choose silently.

## 3. Scope and diffs 🟡

- Keep diffs small. Touch only files listed under "create" and "modify".
- Do not refactor unrelated code. List unrelated issues in the report.
- Do not add features, settings, toggles or screens that the prompt does not list.
- Least code. No dead code. Reuse existing code. No dummy declarations to satisfy a linter.
- Do a cleanup pass before reporting.
- Never rename, swap or weaken tests that the prompt specifies.
- Do not read, copy or adapt code, assets or UI text from other apps' repositories (for example PixelPlayer, whose license is proprietary). Implement from the prompt's spec only. The one exception is the owner's own repo Drift Stream, when a prompt points to it. 🟡 D119, D141

## 4. Git 🟡

- One branch per prompt, named like `p00-bootstrap`, `p01-design-tokens`.
- Never commit directly to `main`.
- Commit locally. Never push. Never merge.
- Never commit anything under `docs/`. It is local-only and git-ignored (D127 ✅).
- The report ends with commit proof: `git status --short` (empty), `git log <base>..HEAD`, `git diff --stat`.

## 5. Architecture rules 🟡 (D13 ✅ Clean Architecture; details assumed)

- `domain` is pure Kotlin. No `android.*` imports in `domain`.
- Dependency direction: `app` → `feature:*`, `data:*`, `playback` → `domain`. `domain` depends on nothing.
- `feature:*` never imports `data:*` or another `feature:*`.
- Repository interfaces live in `domain`. Implementations live in `data:*`.
- Every ViewModel exposes `uiState` (StateFlow), `effects` (Flow) and `onEvent()`. Every list or network screen has loading, error (retry) and empty states. Every screen-level composable has `@Preview` (light and dark). 🟡 D145
- Window insets are applied exactly once: the top-level Scaffold consumes them, each screen owns its insets. Every control and piece of information appears once per screen. 🟡 D145
- Dispatchers are injected, never hardcoded.
- Guard: `domainGuard` in `checkAll` fails on `android.*` imports in `domain`. 🟡 D137

## 6. Design system and motion rules (D16 to D21 ✅, D29 to D33 ✅)

- No hardcoded design values outside token definition files in `core/designsystem`: no raw `Color(0x...)`, no literal dp/sp for radius, size, padding, stroke or elevation, no raw font names, no raw spring or duration numbers. Everything reads tokens. (principle ✅ D21; exact scope 🟡)
- Guards 🟡 D137: `hexGuard` (the owner's existing task) fails on hex colors outside the token files; `motionGuard` fails on `tween(` and linear easing outside the motion tokens.
- Movement, scale and transitions use spring specs from the motion tokens. No `tween(...)` and no linear easing for them. ✅ D29
- Per-frame animated values (translation, scale, alpha, rotation) are applied inside `graphicsLayer { }` (or draw-phase lambdas) and read only there, never during composition. Do not animate size, padding or layout offset. ✅ D32
- Gestures: content follows the finger 1:1 while dragging. On release the animation starts from the gesture's velocity. Any animation can be interrupted by a new touch. ✅ D30
- Ripple is bounded to the component's shape. Haptics go through the haptics abstraction, which respects the user's toggle and strength. ✅ D30
- Button tap animations come from the shared pressable behavior in the design system and use motion tokens. They respect their toggle and reduce-motion mode. ✅ D105, 🟡 D109
- Material 3 Expressive APIs are used only through the wrappers in `core/designsystem`. 🟡 D102
- Buttons, chips, FABs, cards and image containers use the `Melodia*` wrappers from the design system, never raw Material components. D21 ✅, D146 🟡
- Screen-to-screen navigation uses shared-element container transforms. ✅ D31
- Performance claims need measured numbers from a benchmark run. No claim without one. 🟡
- Do not use third-party logos, icons, trademarks or proprietary fonts (for example Spotify's or Apple's) in the "Spotify-inspired" and "Apple-inspired" languages. 🟡 D20

## 7. Customization and toggles (D21 ✅, D28 ✅)

- Every new feature or user-visible behavior gets an entry in the toggle registry with a default value. Id format: `<area>.<name>` 🟡.
- Every new component reads its appearance from tokens so the customization engine can override it.
- Customization state comes from the customization store. It is never hardcoded or kept in composables. 🟡

## 8. Privacy, security, network 🟡 (F-Droid policy facts ✅ verified 2026-11-05)

- No analytics, ads, tracking, or crash reporting that sends data off the device.
- No proprietary SDKs: no Google Play Services, Firebase or similar. Dependencies must be FLOSS. `forbiddenDepGuard` fails the build on them. 🟡 D137
- No network request unless the user enabled the feature or source that needs it.
- No secrets, tokens, passwords or credentials in source, logs, reports or commits. Never print URLs that contain them.
- Stored credentials use Android Keystore-backed encryption. Exact mechanism: TBD ⚠️.
- User-provided API keys and tokens (AI provider, scrobbling, Drive) use the same encrypted storage, are never logged, and are sent only to the provider the user configured.

## 9. Extensions 🟡 (D36 to D39)

- Extensions are separate APKs that run in their own process. Core never loads extension code into its own process.
- Core talks to extensions only through the contract in `extension/api`. Treat all extension data as untrusted: validate it, limit its size, apply timeouts.
- Extensions supply data and a declarative settings schema only. Core draws all UI.
- An extension is usable only after explicit user trust that shows its package name and signing certificate fingerprint.

## 10. Tests 🟡

- Use the exact test names from the prompt. Each edge-case row gets at least one test.
- Pure logic gets unit tests. Framework and coverage targets: TBD.

## 11. Proof in reports 🟡

- Pasted code and test bodies come from `cat` or `grep -n -A<N>` output, never retyped.
- Never write "pasted above". Paste the text again.
- List every inline lint suppression against the suppression budget (budget: TBD).
- Recon: give the file contents, `wc -l`, first and last line, and `sha256sum` taken at the marker commit.
- Visual work: attach screenshots.
- The "Public API" section is pasted from grep output.

## 12. Localization and accessibility (D99, D100, D108 ✅; rule details 🟡)

- All user-visible text comes from string resources. No literal user-facing strings in code. New strings go into the default (English) resource file only.
- Sentences use placeholders and plurals, never string concatenation.
- Layouts use start/end, never left/right, so right-to-left works.
- Defaults meet the verified UX rules (D108): touch targets at least 48dp × 48dp; text contrast at least 4.5:1 (3:1 for large text).
- Every interactive element has a text label or content description. The description does not include the element type and is unique per item in a list. Decorative graphics are marked as unimportant for accessibility.
- User customization may go below these limits; the UI warns and does not block (D110 🟡).
- Guard 🟡 D137: `stringGuard` fails on literal user-facing strings in code.

## 13. Performance (D111 ✅; rules 🟡, verify against the official Compose performance docs in V36)

- Optimize while building. No speculative micro-optimizations; follow the rules below.
- No work during composition: no sorting, filtering, formatting, large allocations, disk or network access. Do it in the ViewModel or a use case and expose ready UI state.
- UI state classes are immutable. Lazy lists use stable keys. Images are decoded at display size.
- Expensive work runs off the main thread on injected dispatchers.
- No unbounded caches and no in-memory copy of the whole library; page or stream.
- Release builds enable code shrinking and resource shrinking.
- A prompt that touches animation, lists, startup or playback ends with a performance check in the report: what was measured, how, and the numbers. "Looks smooth" is not a measurement.
- Budgets (startup time, jank limit, APK size, memory): TBD (set in P57).
