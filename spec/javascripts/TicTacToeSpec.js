// Generated by CoffeeScript 1.6.2
(function() {
  describe("TicTacToe.resetBoard", function() {
    beforeEach(function() {
      affix("#board");
      return spyOn(TicTacToe.display, "resetBoard");
    });
    it("should reset TicTacToe.boardState to an empty board", function() {
      TicTacToe.boardState = "blahblah";
      TicTacToe.resetBoard("X");
      return expect(TicTacToe.boardState).toBe("_________");
    });
    it("should reset TicTacToe.buttonsEnabled to false", function() {
      TicTacToe.buttonsEnabled = true;
      TicTacToe.resetBoard("X");
      return expect(TicTacToe.buttonsEnabled).toBe(false);
    });
    return it("should pass event handler to display.resetBoard which calls makeMove", function() {
      var eventFn;
      spyOn(TicTacToe, "makeMove");
      TicTacToe.resetBoard("X");
      eventFn = TicTacToe.display.resetBoard.calls[0].args[0];
      expect(eventFn(0)).toBe(void 0);
      TicTacToe.buttonsEnabled = true;
      eventFn(0);
      return expect(TicTacToe.makeMove).toHaveBeenCalledWith("X", "_________", 0);
    });
  });

  describe("TicTacToe.displayForm", function() {
    return it("passes an event handler to call initializeGame to  display.displayForm()", function() {
      spyOn(TicTacToe, "initializeGame");
      spyOn(TicTacToe.display, "displayForm");
      TicTacToe.displayForm();
      expect(TicTacToe.initializeGame).not.toHaveBeenCalled();
      TicTacToe.display.displayForm.calls[0].args[0]();
      return expect(TicTacToe.initializeGame).toHaveBeenCalled();
    });
  });

  describe("TicTacToe.disable/enable Buttons", function() {
    var eventFn;
    eventFn = null;
    beforeEach(function() {
      spyOn(TicTacToe, "makeMove");
      spyOn(TicTacToe.display, "resetBoard");
      TicTacToe.resetBoard("X");
      return eventFn = TicTacToe.display.resetBoard.calls[0].args[0];
    });
    it("buttons should do nothing by default", function() {
      eventFn(0);
      return expect(TicTacToe.makeMove).not.toHaveBeenCalled();
    });
    it("should enable all buttons", function() {
      TicTacToe.enableButtons();
      eventFn(0);
      return expect(TicTacToe.makeMove).toHaveBeenCalledWith("X", "_________", 0);
    });
    return it("should be able to disable buttons again", function() {
      TicTacToe.enableButtons();
      TicTacToe.disableButtons();
      eventFn(0);
      return expect(TicTacToe.makeMove).not.toHaveBeenCalled();
    });
  });

  describe("TicTacToe.getNewBoardState", function() {
    return it("should replace first element", function() {
      return expect(TicTacToe.getNewBoardState("X", "__O______", 0)).toBe("X_O______");
    });
  });

  describe("TicTacToe.getNewBoardState", function() {
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

  describe("TicTacToe.updateBoardHuman", function() {
    beforeEach(function() {
      return spyOn(TicTacToe.display, "makeMove");
    });
    it("should call display.makeMove with the given marker/cell", function() {
      TicTacToe.updateBoardHuman("X", "_________", 0);
      return expect(TicTacToe.display.makeMove).toHaveBeenCalledWith("X", 0);
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

  describe("TicTacToe.updateBoardAI", function() {
    beforeEach(function() {
      TicTacToe.buttonsEnabled = false;
      spyOn(TicTacToe.display, "displayWinMessage");
      spyOn(TicTacToe.display, "displayLoseMessage");
      spyOn(TicTacToe.display, "displayTieMessage");
      spyOn(TicTacToe.display, "makeMove");
      return spyOn(TicTacToe, "displayForm");
    });
    it("should call display.makeMove with the given marker/cell", function() {
      TicTacToe.updateBoardAI("O", "X___O____", 4, null);
      return expect(TicTacToe.display.makeMove).toHaveBeenCalledWith("O", 4);
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
      expect(TicTacToe.display.displayWinMessage).toHaveBeenCalled();
      expect(TicTacToe.displayForm).toHaveBeenCalled();
      return expect(TicTacToe.buttonsEnabled).toBe(false);
    });
    it("should display lose message and form when player loses, buttons should be disabled", function() {
      TicTacToe.updateBoardAI("X", "X_OOX_XOX", 8, "L");
      expect(TicTacToe.display.displayLoseMessage).toHaveBeenCalled();
      expect(TicTacToe.displayForm).toHaveBeenCalled();
      return expect(TicTacToe.buttonsEnabled).toBe(false);
    });
    return it("should display tie message and form when player ties, buttons should be disabled", function() {
      TicTacToe.updateBoardAI("X", "XOXOOXXXO", 5, "T");
      expect(TicTacToe.display.displayTieMessage).toHaveBeenCalled();
      expect(TicTacToe.displayForm).toHaveBeenCalled();
      return expect(TicTacToe.buttonsEnabled).toBe(false);
    });
  });

  describe("TicTacToe.makeMove", function() {
    it("should send an asynchronous POST request to /game", function() {
      var flag;
      flag = false;
      spyOn(TicTacToe, "updateBoardHuman");
      spyOn(TicTacToe, "updateBoardAI");
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
      waitsFor((function() {
        return flag;
      }), "Should call makeMove.", 1000);
      runs(function() {
        expect($.ajax).toHaveBeenCalled();
        expect($.ajax.mostRecentCall.args[0].async).toBe(true);
        expect($.ajax.mostRecentCall.args[0].dataType).toBe("json");
        expect($.ajax.mostRecentCall.args[0].type).toBe("POST");
        expect($.ajax.mostRecentCall.args[0].url).toBe("/game");
        expect(TicTacToe.updateBoardHuman).toHaveBeenCalledWith("X", "X________", 0);
        return expect(TicTacToe.updateBoardAI).toHaveBeenCalledWith("O", "X___O____", 4, null);
      });
      return TicTacToe.makeMove("X", "_________", 0);
    });
    return it("should disable buttons while waiting for server, enable buttons when move is returned", function() {
      var flag;
      flag = false;
      TicTacToe.buttonsEnabled = true;
      spyOn(TicTacToe.display, "makeMove");
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
      waitsFor((function() {
        return flag;
      }), "Should call makeMove.", 1000);
      runs(function() {
        return expect(TicTacToe.buttonsEnabled).toBe(true);
      });
      expect(TicTacToe.buttonsEnabled).toBe(true);
      TicTacToe.makeMove("X", "_________", 0);
      return expect(TicTacToe.buttonsEnabled).toBe(false);
    });
  });

  describe("TicTacToe.initializeGame", function() {
    beforeEach(function() {
      spyOn(TicTacToe, "resetBoard");
      spyOn(TicTacToe, "updateBoardAI");
      spyOn(TicTacToe.display, "getMarker").andReturn("X");
      return spyOn(TicTacToe.display, "getMove").andReturn("0");
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
      waitsFor((function() {
        return flag;
      }), "Should call initialize game.", 1000);
      runs(function() {
        expect($.ajax).toHaveBeenCalled();
        expect($.ajax.mostRecentCall.args[0].async).toBe(true);
        expect($.ajax.mostRecentCall.args[0].dataType).toBe("json");
        expect($.ajax.mostRecentCall.args[0].type).toBe("POST");
        expect($.ajax.mostRecentCall.args[0].url).toBe("/");
        expect(TicTacToe.resetBoard).toHaveBeenCalledWith("X");
        return expect(TicTacToe.updateBoardAI).toHaveBeenCalledWith("O", "_________", -1, null);
      });
      return TicTacToe.initializeGame();
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
      waitsFor((function() {
        return flag;
      }), "Should call initialize game.", 1000);
      runs(function() {
        expect(TicTacToe.resetBoard).toHaveBeenCalledWith("X");
        return expect(TicTacToe.updateBoardAI).toHaveBeenCalledWith("O", "O________", 0, null);
      });
      return TicTacToe.initializeGame();
    });
  });

}).call(this);
