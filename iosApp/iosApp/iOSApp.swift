import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        KoinKt.doInitKoin()
        MainViewControllerKt.setupFirebase()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
