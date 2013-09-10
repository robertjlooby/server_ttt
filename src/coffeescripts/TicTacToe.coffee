TicTacToe =
  makeMove: (marker, boardState) -> boardState

  requestBuilder : ->
    request = new XMLHttpRequest()
    request.open("POST", "/game", true)
    request

(exports ? this).TicTacToe = TicTacToe
