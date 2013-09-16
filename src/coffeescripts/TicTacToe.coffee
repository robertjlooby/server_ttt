TicTacToe =
  resetBoard: (marker) ->
    $("div#board").html("<div id=\"row0\"><div id=\"cell0\"><button type=\"button\" class=\"button\" onclick=\"TicTacToe.makeMove('#{marker}', '_________', 0)\">0</button></div><div id=\"cell1\"><button type=\"button\" class=\"button\" onclick=\"TicTacToe.makeMove('#{marker}', '_________', 1)\">1</button></div><div id=\"cell2\"><button type=\"button\" class=\"button\" onclick=\"TicTacToe.makeMove('#{marker}', '_________', 2)\">2</button></div></div><div id=\"row1\"><div id=\"cell3\"><button type=\"button\" class=\"button\" onclick=\"TicTacToe.makeMove('#{marker}', '_________', 3)\">3</button></div><div id=\"cell4\"><button type=\"button\" class=\"button\" onclick=\"TicTacToe.makeMove('#{marker}', '_________', 4)\">4</button></div><div id=\"cell5\"><button type=\"button\" class=\"button\" onclick=\"TicTacToe.makeMove('#{marker}', '_________', 5)\">5</button></div></div><div id=\"row2\"><div id=\"cell6\"><button type=\"button\" class=\"button\" onclick=\"TicTacToe.makeMove('#{marker}', '_________', 6)\">6</button></div><div id=\"cell7\"><button type=\"button\" class=\"button\" onclick=\"TicTacToe.makeMove('#{marker}', '_________', 7)\">7</button></div><div id=\"cell8\"><button type=\"button\" class=\"button\" onclick=\"TicTacToe.makeMove('#{marker}', '_________', 8)\">8</button></div></div>")

  hideButtons: ->
    $(".button").hide()

  showButtons: ->
    $(".button").show()

  getNewBoardState: (marker, boardState, cellNum) ->
    "#{boardState.slice(0, cellNum)}#{marker}#{boardState.slice(cellNum + 1)}"

  setUpBoard: (marker) ->
    $("form").hide()
    TicTacToe.resetBoard(marker)
    TicTacToe.hideButtons()

  updateBoardHuman: (marker, boardState, move) ->
    TicTacToe.hideButtons()
    $("#cell#{move}").html(marker)
    $(".button").each ->
      $(this).attr("onclick", (index, oldHtml) ->
        oldHtml.replace(/[XO_]{9}/, boardState))

  updateBoardAI: (aiMarker, boardState, aiMove, result) ->
    $("#cell#{aiMove}").html(aiMarker)
    $(".button").each ->
      $(this).attr("onclick", (index, oldHtml) ->
        oldHtml.replace(/[XO_]{9}/, boardState))
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
        TicTacToe.showButtons()

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
