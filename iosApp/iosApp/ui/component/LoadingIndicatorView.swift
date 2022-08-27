import SwiftUI

struct LoadingIndicatorView: View {
	
	let style = StrokeStyle(lineWidth: 3, lineCap: .round)
	@State var animate = false
	let color = Color("AccentColor")
	
    var body: some View {
		ZStack {
			Circle()
				.trim(from: 0, to: 0.2)
				.stroke(
					AngularGradient(
						gradient: .init(colors: [color]),
						center: .center
					),
					style: style
				)
				.rotationEffect(Angle(degrees: animate ? 360 : 0))
				.animation(Animation.linear(duration: 0.7).repeatForever(autoreverses: false))
				.frame(width: 100, height: 50)
			
			Circle()
				.trim(from: 0.5, to: 0.7)
				.stroke(
					AngularGradient(
						gradient: .init(colors: [color]),
						center: .center
					),
					style: style
				)
				.rotationEffect(Angle(degrees: animate ? 360 : 0))
				.animation(Animation.linear(duration: 0.7).repeatForever(autoreverses: false))
				.frame(width: 100, height: 50)
	
		}
		.padding(16)
		.background(Color("Background"))
		.edgesIgnoringSafeArea(.all)
		.onAppear(){
			self.animate.toggle()
		}
    }
}

struct LoadingIndicatorView_Previews: PreviewProvider {
    static var previews: some View {
        LoadingIndicatorView()
    }
}