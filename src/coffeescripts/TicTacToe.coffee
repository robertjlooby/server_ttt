TicTacToe =
  replaceBoard: (boardString) ->
    $("div#board").html(boardString)

  makeMove: (marker, boardState) ->
    $.ajax({data:
              marker: marker
              board_state: boardState
            async: true
            type: "POST"
            url: "/game"
            success: (response) -> TicTacToe.replaceBoard(response)})

  initializeGame: ->
    marker = $("input[name='marker']:checked").val()
    move = $("input[name='move']:checked").val()
    $.ajax({data:
              marker: marker
              move: move
            async: true
            type: "POST"
            url: "/"
            success: (response) -> TicTacToe.replaceBoard(response)})

(exports ? this).TicTacToe = TicTacToe
