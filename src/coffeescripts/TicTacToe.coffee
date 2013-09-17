TicTacToe =
  buttonsEnabled: false
  boardState: "_________"
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
    TicTacToe.buttonsEnabled = false
    TicTacToe.boardState = "_________"
    for cell in [0..8]
      do (cell) ->
        $("#cell#{cell} .button").click( ->
          if TicTacToe.buttonsEnabled
            TicTacToe.makeMove(marker, TicTacToe.boardState, cell))

  disableButtons: ->
    TicTacToe.buttonsEnabled = false

  enableButtons: ->
    TicTacToe.buttonsEnabled = true

  getNewBoardState: (marker, boardState, cellNum) ->
    "#{boardState.slice(0, cellNum)}#{marker}#{boardState.slice(cellNum + 1)}"

  setUpBoard: (marker) ->
    $("form").hide()
    TicTacToe.resetBoard(marker)

  updateBoardHuman: (marker, boardState, move) ->
    TicTacToe.disableButtons()
    $("#cell#{move}").html(marker)
    TicTacToe.boardState = boardState

  updateBoardAI: (aiMarker, boardState, aiMove, result) ->
    $("#cell#{aiMove}").html(aiMarker)
    TicTacToe.boardState = boardState
    switch result
      when "W"
        $("#board").prepend("<h1>You Win!</h1>")
        $("form").show()
      when "L"
        $("#board").prepend("<h1>You Lose!</h1>")
        $("form").show()
      when "T"
        $("#board").prepend("<h1>It is a Tie!</h1>")
        $("form").show()
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
    marker = $("input[name='marker']:checked").val()
    move = $("input[name='move']:checked").val()
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
