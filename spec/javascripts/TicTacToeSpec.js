// Generated by CoffeeScript 1.6.2
(function() {
  describe("resetBoard", function() {
    beforeEach(function() {
      return affix("#board");
    });
    it("should reset the contents of #board", function() {
      TicTacToe.resetBoard("X");
      return expect($("div#board").html()).toMatch("TicTacToe\.makeMove\\('X', '_________', 4\\)");
    });
    return it("should reset the contents of #board with the given marker", function() {
      TicTacToe.resetBoard("O");
      return expect($("div#board").html()).toMatch("TicTacToe\.makeMove\\('O', '_________', 8\\)");
    });
  });

  describe("button visibility", function() {
    beforeEach(function() {
      affix("#board");
      return TicTacToe.resetBoard("X");
    });
    it("should hide all .button 's", function() {
      TicTacToe.hideButtons();
      return $(".button").each(function() {
        return expect($(this).css("display")).toBe("none");
      });
    });
    return it("should show all .button 's", function() {
      TicTacToe.hideButtons();
      TicTacToe.showButtons();
      return $(".button").each(function() {
        return expect($(this).css("display")).toBe("inline-block");
      });
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
    it("should reset the board", function() {
      TicTacToe.setUpBoard("X");
      return expect($(".button").size()).toBe(9);
    });
    return it("should hide all .button 's", function() {
      TicTacToe.setUpBoard("X");
      return $(".button").each(function() {
        return expect($(this).css("display")).toBe("none");
      });
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
    it("should hide all the buttons", function() {
      TicTacToe.updateBoardHuman("X", "_________", 0);
      return $(".button").each(function() {
        return expect($(this).css("display")).toMatch("none");
      });
    });
    return it("should update the boardState in all the buttons", function() {
      TicTacToe.updateBoardHuman("X", "X________", 0);
      return $(".button").each(function() {
        return expect($(this).attr("onclick")).toMatch("X________");
      });
    });
  });

  describe("updateBoardAI", function() {
    beforeEach(function() {
      affix("form");
      $("form").hide();
      affix("#board");
      TicTacToe.resetBoard("X");
      return TicTacToe.hideButtons();
    });
    it("should make the aiMove in the given cell", function() {
      TicTacToe.updateBoardAI("O", "X___O____", 4, null);
      return expect($("#cell4").html()).toBe("O");
    });
    it("should update the boardState in all the buttons", function() {
      TicTacToe.updateBoardAI("O", "X___O____", 4, null);
      return $(".button").each(function() {
        return expect($(this).attr("onclick")).toMatch("X___O____");
      });
    });
    it("should make all buttons visible again", function() {
      TicTacToe.updateBoardAI("O", "X___O____", 4, null);
      return $(".button").each(function() {
        return expect($(this).css("display")).toMatch("inline-block");
      });
    });
    it("should display win message and form when player wins, not buttons", function() {
      TicTacToe.updateBoardAI("O", "X_OOX_XOX", 7, "W");
      expect($("h1").html()).toMatch("Win");
      expect($("form").css("display")).toBe("block");
      return $(".button").each(function() {
        return expect($(this).css("display")).toMatch("none");
      });
    });
    it("should display lose message and form when player loses, not buttons", function() {
      TicTacToe.updateBoardAI("X", "X_OOX_XOX", 8, "L");
      expect($("h1").html()).toMatch("Lose");
      expect($("form").css("display")).toBe("block");
      return $(".button").each(function() {
        return expect($(this).css("display")).toMatch("none");
      });
    });
    return it("should display tie message and form when player ties, not buttons", function() {
      TicTacToe.updateBoardAI("X", "XOXOOXXXO", 5, "T");
      expect($("h1").html()).toMatch("Tie");
      expect($("form").css("display")).toBe("block");
      return $(".button").each(function() {
        return expect($(this).css("display")).toMatch("none");
      });
    });
  });

  describe("makeMove", function() {
    beforeEach(function() {
      affix("#board");
      TicTacToe.resetBoard("X");
      return affix("form").hide();
    });
    it("should send an asynchronous POST request to /game", function() {
      var flag;
      spyOn($, "ajax").andCallFake(function(params) {
        return params.success({
          "boardState": "X___O____",
          "aiMove": 4,
          "result": null
        });
      });
      flag = false;
      runs(function() {
        return setTimeout((function() {
          TicTacToe.makeMove("X", "_________", 0);
          return flag = true;
        }), 0);
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
        expect($("#board").html()).toMatch("TicTacToe\.makeMove\\('X', 'X___O____', 3\\)");
        return $(".button").each(function() {
          return expect($(this).css("display")).toBe("inline-block");
        });
      });
    });
    it("show 'Tie'/form and not buttons for tie", function() {
      var flag;
      spyOn($, "ajax").andCallFake(function(params) {
        return params.success({
          "boardState": "XOXXOOOXX",
          "aiMove": -1,
          "result": "T"
        });
      });
      flag = false;
      runs(function() {
        return setTimeout((function() {
          TicTacToe.makeMove("X", "XOXXOOOX_", 8);
          return flag = true;
        }), 0);
      });
      waitsFor((function() {
        return flag;
      }), "Should call makeMove.", 1000);
      return runs(function() {
        expect($("h1").html()).toMatch("Tie");
        expect($("form").css("display")).toBe("block");
        return $(".button").each(function() {
          return expect($(this).css("display")).toBe("none");
        });
      });
    });
    it("show 'Tie'/form and not buttons for tie after AI move", function() {
      var flag;
      spyOn($, "ajax").andCallFake(function(params) {
        return params.success({
          "boardState": "OXOOXXXO_",
          "aiMove": 8,
          "result": "T"
        });
      });
      flag = false;
      runs(function() {
        return setTimeout((function() {
          TicTacToe.makeMove("X", "OXOOXX_O_", 6);
          return flag = true;
        }), 0);
      });
      waitsFor((function() {
        return flag;
      }), "Should call makeMove.", 1000);
      return runs(function() {
        expect($("h1").html()).toMatch("Tie");
        expect($("form").css("display")).toBe("block");
        return $(".button").each(function() {
          return expect($(this).css("display")).toBe("none");
        });
      });
    });
    it("show 'Win'/form and not buttons for player win", function() {
      var flag;
      spyOn($, "ajax").andCallFake(function(params) {
        return params.success({
          "boardState": "X_OOO_XXX",
          "aiMove": -1,
          "result": "W"
        });
      });
      flag = false;
      runs(function() {
        return setTimeout((function() {
          TicTacToe.makeMove("X", "X_OOO_X_X", 7);
          return flag = true;
        }), 0);
      });
      waitsFor((function() {
        return flag;
      }), "Should call makeMove.", 1000);
      return runs(function() {
        expect($("h1").html()).toMatch("Win");
        expect($("form").css("display")).toBe("block");
        return $(".button").each(function() {
          return expect($(this).css("display")).toBe("none");
        });
      });
    });
    return it("show 'Lose'/form and not buttons for player loss", function() {
      var flag;
      spyOn($, "ajax").andCallFake(function(params) {
        return params.success({
          "boardState": "OXXOO_O_X",
          "aiMove": 3,
          "result": "L"
        });
      });
      flag = false;
      runs(function() {
        return setTimeout((function() {
          TicTacToe.makeMove("X", "OX__O_O_X", 2);
          return flag = true;
        }), 0);
      });
      waitsFor((function() {
        return flag;
      }), "Should call makeMove.", 1000);
      return runs(function() {
        expect($("h1").html()).toMatch("Lose");
        expect($("form").css("display")).toBe("block");
        return $(".button").each(function() {
          return expect($(this).css("display")).toBe("none");
        });
      });
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
      spyOn($, "ajax").andCallFake(function(params) {
        return params.success({
          "boardState": "_________",
          "aiMove": -1,
          "result": null
        });
      });
      flag = false;
      runs(function() {
        return setTimeout((function() {
          TicTacToe.initializeGame();
          return flag = true;
        }), 0);
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
        expect($("#board").html()).toMatch("TicTacToe\.makeMove\\('X', '_________', 4\\)");
        return $(".button").each(function() {
          return expect($(this).css("display")).toBe("inline-block");
        });
      });
    });
    return it("should initialize game when moving second", function() {
      var flag;
      spyOn($, "ajax").andCallFake(function(params) {
        return params.success({
          "boardState": "O________",
          "aiMove": 0,
          "result": null
        });
      });
      flag = false;
      runs(function() {
        return setTimeout((function() {
          TicTacToe.initializeGame();
          return flag = true;
        }), 0);
      });
      waitsFor((function() {
        return flag;
      }), "Should call initialize game.", 1000);
      return runs(function() {
        return expect($("#board").html()).toMatch("TicTacToe\.makeMove\\('X', 'O________', 4\\)");
      });
    });
  });

}).call(this);
