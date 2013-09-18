CSSDisplay =
  resetBoard: (marker) ->
    str = ""
    for row in [0..2]
      str += "<div id=\"row#{row}\">"
      for col in [0..2]
        cell = (3 * row) + col
        str += "<div id=\"cell#{cell}\">
                  <button type=\"button\" class=\"button\">#{cell}</button>
                </div>"
      str += "</div>"
    $("#board").html(str)

  displayForm: ->
    str = "<div id=\"newGameForm\">
            <div>
              Marker:
                <input type=\"radio\" name=\"marker\" value=\"X\" checked> X
                <input type=\"radio\" name=\"marker\"   value=\"O\"> O
            </div>
            <div>
              Move:
                <input type=\"radio\" name=\"move\" value=\"0\" checked> First
                <input type=\"radio\" name=\"move\" value=\"1\"> Second
            </div>
            <button type=\"button\" id=\"newGameButton\">Play!</button>
          </div>"
    $("#board").append(str)
    $("#newGameButton")

  getCell: (cellNum) ->
    $("#cell#{cellNum}")

  getButton: (cellNum) ->
    $("#cell#{cellNum} .button")

  getMarker: ->
    $("input[name='marker']:checked").val()

  getMove: ->
    $("input[name='move']:checked").val()

  displayWinMessage: ->
    $("#board").prepend("<h1>You Win!</h1>")

  displayLoseMessage: ->
    $("#board").prepend("<h1>You Lose!</h1>")

  displayTieMessage: ->
    $("#board").prepend("<h1>It is a Tie!</h1>")
  
TicTacToe =
  display: CSSDisplay
  buttonsEnabled: false
  boardState: "_________"
  resetBoard: (marker) ->
    TicTacToe.buttonsEnabled = false
    TicTacToe.boardState = "_________"
    TicTacToe.display.resetBoard(marker)
    for cell in [0..8]
      do (cell) ->
        TicTacToe.display.getButton(cell).click( ->
          if TicTacToe.buttonsEnabled
            TicTacToe.makeMove(marker, TicTacToe.boardState, cell))

  displayForm: ->
    button = TicTacToe.display.displayForm()
    button.click( -> TicTacToe.initializeGame())

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
    TicTacToe.display.getCell(move).html(marker)
    TicTacToe.boardState = boardState

  updateBoardAI: (aiMarker, boardState, aiMove, result) ->
    TicTacToe.display.getCell(aiMove).html(aiMarker)
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

(exports ? this).CSSDisplay = CSSDisplay
(exports ? this).TicTacToe = TicTacToe

$ -> TicTacToe.displayForm()
