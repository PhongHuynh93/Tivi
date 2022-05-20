//
//  DiscoverView.swift
//  iosApp
//
//  Created by Huynh Phong on 19/05/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Tivi

struct DiscoverView: View {

  @ObservedObject var observable = ObservableViewModel<DiscoverViewModel, DiscoverShowState, DiscoverShowEffect>(
    viewModel: DiscoverViewModel()
  )

  var body: some View {
    NavigationView {

      VStack {
        if observable.state is DiscoverShowState.InProgress {
          LoadingIndicatorView()
        } else if observable.state is DiscoverShowState.Success {

          let result = observable.state as? DiscoverShowState.Success

          BodyContentView(showResult: result!.data)

        }
      }
        .ignoresSafeArea()
        .navigationBarHidden(true)
    }
      .accentColor(Color.background)
      .navigationViewStyle(StackNavigationViewStyle())
  }

}

// Screen Bounds...
extension View {
  func getRect() -> CGRect {
    UIScreen.main.bounds
  }
}

struct DiscoverView_Previews: PreviewProvider {
  static var previews: some View {
    DiscoverView()

    DiscoverView()
      .preferredColorScheme(.dark)
  }
}
