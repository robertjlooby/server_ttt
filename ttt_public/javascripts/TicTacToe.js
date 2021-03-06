// Generated by CoffeeScript 1.6.2
(function() {
  var TicTacToe;

  TicTacToe = {
    display: CSSDisplay,
    buttonsEnabled: false,
    boardState: "_________",
    resetBoard: function(marker) {
      TicTacToe.buttonsEnabled = false;
      TicTacToe.boardState = "_________";
      return TicTacToe.display.resetBoard(function(cell) {
        if (TicTacToe.buttonsEnabled) {
          return TicTacToe.makeMove(marker, TicTacToe.boardState, cell);
        }
      });
    },
    displayForm: function() {
      return TicTacToe.display.displayForm(function() {
        return TicTacToe.initializeGame();
      });
    },
    disableButtons: function() {
      return TicTacToe.buttonsEnabled = false;
    },
    enableButtons: function() {
      return TicTacToe.buttonsEnabled = true;
    },
    getNewBoardState: function(marker, boardState, cellNum) {
      return "" + (boardState.slice(0, cellNum)) + marker + (boardState.slice(cellNum + 1));
    },
    updateBoardHuman: function(marker, boardState, move) {
      TicTacToe.disableButtons();
      TicTacToe.display.makeMove(marker, move);
      return TicTacToe.boardState = boardState;
    },
    updateBoardAI: function(aiMarker, boardState, aiMove, result) {
      TicTacToe.display.makeMove(aiMarker, aiMove);
      TicTacToe.boardState = boardState;
      switch (result) {
        case "W":
          TicTacToe.display.displayWinMessage();
          return TicTacToe.displayForm();
        case "L":
          TicTacToe.display.displayLoseMessage();
          return TicTacToe.displayForm();
        case "T":
          TicTacToe.display.displayTieMessage();
          return TicTacToe.displayForm();
        default:
          return TicTacToe.enableButtons();
      }
    },
    makeMove: function(marker, boardState, cellNum) {
      var aiMarker, newBoardState;
      newBoardState = TicTacToe.getNewBoardState(marker, boardState, cellNum);
      TicTacToe.updateBoardHuman(marker, newBoardState, cellNum);
      aiMarker = marker === "X" ? "O" : "X";
      return $.ajax({
        data: {
          marker: marker,
          board_state: newBoardState
        },
        dataType: "json",
        async: true,
        type: "POST",
        url: "/game",
        success: function(json) {
          return TicTacToe.updateBoardAI(aiMarker, json.boardState, json.aiMove, json.result);
        }
      });
    },
    initializeGame: function() {
      var aiMarker, marker, move;
      marker = TicTacToe.display.getMarker();
      move = TicTacToe.display.getMove();
      TicTacToe.resetBoard(marker);
      aiMarker = marker === "X" ? "O" : "X";
      return $.ajax({
        data: {
          marker: marker,
          move: move
        },
        dataType: "json",
        async: true,
        type: "POST",
        url: "/",
        success: function(json) {
          return TicTacToe.updateBoardAI(aiMarker, json.boardState, json.aiMove, json.result);
        }
      });
    }
  };

  (typeof exports !== "undefined" && exports !== null ? exports : this).TicTacToe = TicTacToe;

  $(function() {
    return TicTacToe.displayForm();
  });

}).call(this);
