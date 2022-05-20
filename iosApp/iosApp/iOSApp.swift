import SwiftUI
import Tivi

@main
struct iOSApp: App {

  init() {
    // Perform any tasks on app launch
    let userDefaults = UserDefaults(suiteName: "MULTIPLATFORM_SETTINGS")!
    let doOnStartup = { NSLog("Hello from iOS/Swift!") }

    KoinIOSKt.doInitKoinIos(
      userDefaults: userDefaults,
      doOnStartup: doOnStartup
    )
  }

  var body: some Scene {
		WindowGroup {
      DiscoverView()
		}
	}
}
