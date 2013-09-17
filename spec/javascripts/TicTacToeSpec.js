// Generated by CoffeeScript 1.6.2
(function() {
  describe("resetBoard", function() {
    beforeEach(function() {
      return affix("#board");
    });
    it("should reset the contents of #board", function() {
      TicTacToe.resetBoard("X");
      expect($("div#board").html()).toMatch("class=\"button\">0<");
      expect($("div#board").html()).toMatch("<div id=\"row2\">");
      return expect($("div#board").html()).toMatch("<div id=\"cell8\">");
    });
    it("should reset TicTacToe.boardState to an empty board", function() {
      TicTacToe.boardState = "blahblah";
      TicTacToe.resetBoard("X");
      return expect(TicTacToe.boardState).toBe("_________");
    });
    return it("should reset TicTacToe.buttonsEnabled to false", function() {
      TicTacToe.buttonsEnabled = true;
      TicTacToe.resetBoard("X");
      return expect(TicTacToe.buttonsEnabled).toBe(false);
    });
  });

  describe("button clickability", function() {
    beforeEach(function() {
      affix("#board");
      return TicTacToe.resetBoard("X");
    });
    it("buttons should do nothing by default", function() {
      var initialBoard;
      initialBoard = $("#board").html();
      spyOn(TicTacToe, "makeMove");
      $(".button").each(function() {
        $(this).click();
        return expect($("#board").html()).toBe(initialBoard);
      });
      return expect(TicTacToe.makeMove).not.toHaveBeenCalled();
    });
    it("should enable all buttons", function() {
      var cell, _i, _results;
      spyOn(TicTacToe, "makeMove");
      TicTacToe.enableButtons();
      $(".button").each(function() {
        return $(this).click();
      });
      expect(TicTacToe.makeMove.calls.length).toBe(9);
      _results = [];
      for (cell = _i = 0; _i <= 8; cell = ++_i) {
        _results.push(expect(TicTacToe.makeMove).toHaveBeenCalledWith("X", "_________", cell));
      }
      return _results;
    });
    return it("should be able to disable buttons again", function() {
      var initialBoard;
      initialBoard = $("#board").html();
      spyOn(TicTacToe, "makeMove");
      TicTacToe.enableButtons();
      TicTacToe.disableButtons();
      $(".button").each(function() {
        $(this).click();
        return expect($("#board").html()).toBe(initialBoard);
      });
      return expect(TicTacToe.makeMove).not.toHaveBeenCalled();
    });
  });

  describe("getNewBoardState", function() {
    return it("should replace first element", function() {
      return expect(TicTacToe.getNewBoardState("X", "__O______", 0)).toBe("X_O______");
    });
  });

  describe("getNewBoardState", function() {
    it("should replace first element", function() {
      return expect(TicTacToe.getNewBoardState("X", "__O______", 0)).toBe("X_O______");
    });
    it("should replace an element in the middle", function() {
      return expect(TicTacToe.getNewBoardState("O", "X_OX_____", 6)).toBe("X_OX__O__");
    });
    return it("should replace an element at the end", function() {
      return expect(TicTacToe.getNewBoardState("X", "X_OX__O__", 8)).toBe("X_OX__O_X");
    });
  });

  describe("setUpBoard", function() {
    beforeEach(function() {
      affix("form");
      return affix("#board");
    });
    it("should hide the form", function() {
      TicTacToe.setUpBoard("X");
      return expect($("form").css("display")).toBe("none");
    });
    return it("should reset the board", function() {
      TicTacToe.setUpBoard("X");
      expect($(".button").size()).toBe(9);
      return expect(TicTacToe.boardState).toBe("_________");
    });
  });

  describe("updateBoardHuman", function() {
    beforeEach(function() {
      affix("#board");
      return TicTacToe.resetBoard("X");
    });
    it("should make the human move in a given cell", function() {
      TicTacToe.updateBoardHuman("X", "_________", 0);
      return expect($("#cell0").html()).toBe("X");
    });
    it("should disable buttons", function() {
      TicTacToe.updateBoardHuman("X", "_________", 0);
      return expect(TicTacToe.buttonsEnabled).toBe(false);
    });
    return it("should update the boardState", function() {
      TicTacToe.updateBoardHuman("X", "X________", 0);
      return expect(TicTacToe.boardState).toBe("X________");
    });
  });

  describe("updateBoardAI", function() {
    beforeEach(function() {
      affix("form");
      $("form").hide();
      affix("#board");
      return TicTacToe.resetBoard("X");
    });
    it("should make the aiMove in the given cell", function() {
      TicTacToe.updateBoardAI("O", "X___O____", 4, null);
      return expect($("#cell4").html()).toBe("O");
    });
    it("should update the boardState", function() {
      TicTacToe.updateBoardAI("O", "X___O____", 4, null);
      return expect(TicTacToe.boardState).toBe("X___O____");
    });
    it("should enable buttons again", function() {
      TicTacToe.updateBoardAI("O", "X___O____", 4, null);
      return expect(TicTacToe.buttonsEnabled).toBe(true);
    });
    it("should display win message and form when player wins, buttons should be disabled", function() {
      TicTacToe.updateBoardAI("O", "X_OOX_XOX", 7, "W");
      expect($("h1").html()).toMatch("Win");
      expect($("form").css("display")).toBe("block");
      return expect(TicTacToe.buttonsEnabled).toBe(false);
    });
    it("should display lose message and form when player loses, buttons should be disabled", function() {
      TicTacToe.updateBoardAI("X", "X_OOX_XOX", 8, "L");
      expect($("h1").html()).toMatch("Lose");
      expect($("form").css("display")).toBe("block");
      return expect(TicTacToe.buttonsEnabled).toBe(false);
    });
    return it("should display tie message and form when player ties, buttons should be disabled", function() {
      TicTacToe.updateBoardAI("X", "XOXOOXXXO", 5, "T");
      expect($("h1").html()).toMatch("Tie");
      expect($("form").css("display")).toBe("block");
      return expect(TicTacToe.buttonsEnabled).toBe(false);
    });
  });

  describe("makeMove", function() {
    beforeEach(function() {
      affix("#board");
      TicTacToe.resetBoard("X");
      TicTacToe.enableButtons();
      return affix("form").hide();
    });
    it("should send an asynchronous POST request to /game", function() {
      var flag;
      flag = false;
      spyOn($, "ajax").andCallFake(function(params) {
        return setTimeout((function() {
          params.success({
            "boardState": "X___O____",
            "aiMove": 4,
            "result": null
          });
          return flag = true;
        }), 0);
      });
      runs(function() {
        return TicTacToe.makeMove("X", "_________", 0);
      });
      waitsFor((function() {
        return flag;
      }), "Should call makeMove.", 1000);
      return runs(function() {
        expect($.ajax).toHaveBeenCalled();
        expect($.ajax.mostRecentCall.args[0].async).toBe(true);
        expect($.ajax.mostRecentCall.args[0].dataType).toBe("json");
        expect($.ajax.mostRecentCall.args[0].type).toBe("POST");
        expect($.ajax.mostRecentCall.args[0].url).toBe("/game");
        expect($("form").css("display")).toBe("none");
        expect($("#cell0").html()).toBe("X");
        expect($("#cell4").html()).toBe("O");
        expect(TicTacToe.boardState).toBe("X___O____");
        return expect(TicTacToe.buttonsEnabled).toBe(true);
      });
    });
    it("show 'Tie'/form and disabled buttons for tie", function() {
      var flag;
      flag = false;
      spyOn($, "ajax").andCallFake(function(params) {
        return setTimeout((function() {
          params.success({
            "boardState": "XOXXOOOXX",
            "aiMove": -1,
            "result": "T"
          });
          return flag = true;
        }), 0);
      });
      runs(function() {
        return TicTacToe.makeMove("X", "XOXXOOOX_", 0);
      });
      waitsFor((function() {
        return flag;
      }), "Should call makeMove.", 1000);
      return runs(function() {
        expect($("h1").html()).toMatch("Tie");
        expect($("form").css("display")).toBe("block");
        expect(TicTacToe.boardState).toBe("XOXXOOOXX");
        return expect(TicTacToe.buttonsEnabled).toBe(false);
      });
    });
    it("show 'Tie'/form and disabled buttons for tie after AI move", function() {
      var flag;
      flag = false;
      spyOn($, "ajax").andCallFake(function(params) {
        return setTimeout((function() {
          params.success({
            "boardState": "OXOOXXXOO",
            "aiMove": 8,
            "result": "T"
          });
          return flag = true;
        }), 0);
      });
      runs(function() {
        return TicTacToe.makeMove("X", "OXOOXX_O_", 6);
      });
      waitsFor((function() {
        return flag;
      }), "Should call makeMove.", 1000);
      return runs(function() {
        expect($("h1").html()).toMatch("Tie");
        expect($("form").css("display")).toBe("block");
        expect(TicTacToe.boardState).toBe("OXOOXXXOO");
        return expect(TicTacToe.buttonsEnabled).toBe(false);
      });
    });
    it("show 'Win'/form and disabled buttons for player win", function() {
      var flag;
      flag = false;
      spyOn($, "ajax").andCallFake(function(params) {
        return setTimeout((function() {
          params.success({
            "boardState": "X_OOO_XXX",
            "aiMove": -1,
            "result": "W"
          });
          return flag = true;
        }), 0);
      });
      runs(function() {
        return TicTacToe.makeMove("X", "X_OOO_X_X", 7);
      });
      waitsFor((function() {
        return flag;
      }), "Should call makeMove.", 1000);
      return runs(function() {
        expect($("h1").html()).toMatch("Win");
        expect($("form").css("display")).toBe("block");
        expect(TicTacToe.boardState).toBe("X_OOO_XXX");
        return expect(TicTacToe.buttonsEnabled).toBe(false);
      });
    });
    it("show 'Lose'/form and disabled buttons for player loss", function() {
      var flag;
      flag = false;
      spyOn($, "ajax").andCallFake(function(params) {
        return setTimeout((function() {
          params.success({
            "boardState": "OXXOO_O_X",
            "aiMove": 3,
            "result": "L"
          });
          return flag = true;
        }), 0);
      });
      runs(function() {
        return TicTacToe.makeMove("X", "OX__O_O_X", 2);
      });
      waitsFor((function() {
        return flag;
      }), "Should call makeMove.", 1000);
      return runs(function() {
        expect($("h1").html()).toMatch("Lose");
        expect($("form").css("display")).toBe("block");
        expect(TicTacToe.boardState).toBe("OXXOO_O_X");
        return expect(TicTacToe.buttonsEnabled).toBe(false);
      });
    });
    return it("should disable buttons while waiting for server, enable buttons when move is returned", function() {
      var flag;
      flag = false;
      spyOn($, "ajax").andCallFake(function(params) {
        return setTimeout((function() {
          params.success({
            "boardState": "X___O____",
            "aiMove": 4,
            "result": null
          });
          return flag = true;
        }), 0);
      });
      runs(function() {
        return TicTacToe.makeMove("X", "_________", 0);
      });
      waitsFor((function() {
        return flag;
      }), "Should call makeMove.", 1000);
      runs(function() {
        return expect(TicTacToe.buttonsEnabled).toBe(true);
      });
      return expect(TicTacToe.buttonsEnabled).toBe(true);
    });
  });

  describe("initializeGame", function() {
    beforeEach(function() {
      var form;
      form = affix("form");
      form.affix('input[name="marker"][type="radio"][value="X"]').prop("checked", true);
      form.affix('input[name="move"][type="radio"][value="0"]').prop("checked", true);
      return affix("#board");
    });
    it("should send an asynchronous POST request to / and initialize game", function() {
      var flag;
      flag = false;
      spyOn($, "ajax").andCallFake(function(params) {
        return setTimeout((function() {
          params.success({
            "boardState": "_________",
            "aiMove": -1,
            "result": null
          });
          return flag = true;
        }), 0);
      });
      runs(function() {
        return TicTacToe.initializeGame();
      });
      waitsFor((function() {
        return flag;
      }), "Should call initialize game.", 1000);
      return runs(function() {
        expect($.ajax).toHaveBeenCalled();
        expect($.ajax.mostRecentCall.args[0].async).toBe(true);
        expect($.ajax.mostRecentCall.args[0].dataType).toBe("json");
        expect($.ajax.mostRecentCall.args[0].type).toBe("POST");
        expect($.ajax.mostRecentCall.args[0].url).toBe("/");
        expect($("form").css("display")).toBe("none");
        expect(TicTacToe.boardState).toBe("_________");
        return expect(TicTacToe.buttonsEnabled).toBe(true);
      });
    });
    return it("should initialize game when moving second", function() {
      var flag;
      flag = false;
      spyOn($, "ajax").andCallFake(function(params) {
        return setTimeout((function() {
          params.success({
            "boardState": "O________",
            "aiMove": 0,
            "result": null
          });
          return flag = true;
        }), 0);
      });
      runs(function() {
        return TicTacToe.initializeGame();
      });
      waitsFor((function() {
        return flag;
      }), "Should call initialize game.", 1000);
      return runs(function() {
        expect(TicTacToe.boardState).toBe("O________");
        return expect(TicTacToe.buttonsEnabled).toBe(true);
      });
    });
  });

}).call(this);
