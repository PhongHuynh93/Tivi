import Tivi
import Combine

class ObservableViewModel<VM: BaseViewModel, S: State, E: Effect>: ObservableObject {

  @Published private(set) var state: State
  let viewModel: VM
  let effect = PassthroughSubject<E, Never>()

  init(viewModel: VM) {
    self.viewModel = viewModel
    self.state = State()

    viewModel.observe(
      viewModel.state,
      onChange: { state in
        self.state = state as! S
      })
    viewModel.observe(
      viewModel.effect,
      onChange: { effect in
        self.effect.send(effect as! E)
      })
  }

  deinit {
    viewModel.detach()
  }
}
