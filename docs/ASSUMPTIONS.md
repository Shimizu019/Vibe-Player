# Assumptions

Every assumption and deviation from the spec is recorded here.

## Project state (before Milestone 1)
- The project folder did not contain an Android project yet, so per spec section 2.7 the project will be created by hand, including the Gradle wrapper.
- The five approved screenshots (docs/design/) were not yet available in the repo at the time of writing. They must be added before Milestone 1 UI work begins, since the spec requires element-by-element comparison with them.
- Git identity for commits is the repo owner's GitHub noreply address (Shimizu019@users.noreply.github.com), configured repo-locally.

## Open items to resolve during Milestone 1 (no deviations yet)
- Exact minSdk: spec says 21 if the stack allows, never above 24. To be confirmed against current stable library requirements (Hilt, Media3, Coil 3, WorkManager) and recorded here.
- Exact library versions: will be looked up from Google Maven / Maven Central at build time. No alpha/beta/rc versions.
