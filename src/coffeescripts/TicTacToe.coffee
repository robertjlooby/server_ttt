TicTacToe =
  display: CSSDisplay
  buttonsEnabled: false
  boardState: "_________"
  resetBoard: (marker) ->
    TicTacToe.buttonsEnabled = false
    TicTacToe.boardState = "_________"
    TicTacToe.display.resetBoard( (cell) ->
          if TicTacToe.buttonsEnabled
            TicTacToe.makeMove(marker, TicTacToe.boardState, cell))

  displayForm: ->
    TicTacToe.display.displayForm(-> TicTacToe.initializeGame())

  disableButtons: ->
    TicTacToe.buttonsEnabled = false

  enableButtons: ->
    TicTacToe.buttonsEnabled = true

  getNewBoardState: (marker, boardState, cellNum) ->
    "#{boardState.slice(0, cellNum)}#{marker}#{boardState.slice(cellNum + 1)}"

  setUpBoard: (marker) ->
    TicTacToe.resetBoard(marker)

  updateBoardHuman: (marker, boardState, move) ->
    TicTacToe.disableButtons()
    TicTacToe.display.makeMove(marker, move)
    TicTacToe.boardState = boardState

  updateBoardAI: (aiMarker, boardState, aiMove, result) ->
    TicTacToe.display.makeMove(aiMarker, aiMove)
    TicTacToe.boardState = boardState
    switch result
      when "W"
        TicTacToe.display.displayWinMessage()
        TicTacToe.displayForm()
      when "L"
        TicTacToe.display.displayLoseMessage()
        TicTacToe.displayForm()
      when "T"
        TicTacToe.display.displayTieMessage()
        TicTacToe.displayForm()
      else
        TicTacToe.enableButtons()

  makeMove: (marker, boardState, cellNum) ->
    newBoardState = TicTacToe.getNewBoardState marker, boardState, cellNum
    TicTacToe.updateBoardHuman marker, newBoardState, cellNum
    aiMarker = if (marker == "X") then "O" else "X"
    $.ajax({data:
              marker: marker
              board_state: newBoardState
            dataType: "json"
            async: true
            type: "POST"
            url: "/game"
            success: (json) ->
              TicTacToe.updateBoardAI(aiMarker, json.boardState, json.aiMove, json.result)})

  initializeGame: ->
    marker = TicTacToe.display.getMarker()
    move = TicTacToe.display.getMove()
    TicTacToe.setUpBoard marker
    aiMarker = if (marker == "X") then "O" else "X"
    $.ajax({data:
              marker: marker
              move: move
            dataType: "json"
            async: true
            type: "POST"
            url: "/"
            success: (json) ->
              TicTacToe.updateBoardAI(aiMarker, json.boardState, json.aiMove, json.result)})

(exports ? this).TicTacToe = TicTacToe

$ -> TicTacToe.displayForm()
