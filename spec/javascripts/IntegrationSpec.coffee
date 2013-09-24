describe "integration tests", ->
  aiMoves = null
  beforeEach ->
    affix("#board")
    aiMoves = 0

  xit "should play a tie game", ->
    #initial form display
    TicTacToe.displayForm()

    expect($("#board #newGameForm").length).toBe(1)

    #click button to start new game as X moving first
    spyOn(TicTacToe, "initializeGame").andCallThrough()
    spyOn(TicTacToe, "resetBoard").andCallThrough()
    spyOn(TicTacToe, "makeMove").andCallThrough()
    spyOn(TicTacToe, "updateBoardHuman").andCallThrough()
    spyOn(TicTacToe, "updateBoardAI").andCallThrough()
    humanMoves = [
      {"boardState": "X________", "cellNum": 0}
      {"boardState": "X___O___X", "cellNum": 8}
      {"boardState": "XO__O__XX", "cellNum": 7}
      {"boardState": "XOX_O_OXX", "cellNum": 2}
      {"boardState": "XOXXOOOXX", "cellNum": 3}
    ]
    
    json = [
      {"boardState": "_________", "aiMove": -1, "result": null}
      {"boardState": "X___O____", "aiMove": 4, "result": null}
      {"boardState": "XO__O___X", "aiMove": 1, "result": null}
      {"boardState": "XO__O_OXX", "aiMove": 6, "result": null}
      {"boardState": "XOX_OOOXX", "aiMove": 5, "result": null}
      {"boardState": "XOXXOOOXX", "aiMove": -1, "result": "T"}
    ]
    
    spyOn($, "ajax").andCallFake(
      (params) ->
        setTimeout(( ->
            if aiMoves > 0
              realArgs = TicTacToe.updateBoardHuman.mostRecentCall.args
              expArgs  = humanMoves[aiMoves-1]
              expect(realArgs[0]).toBe("X")
              expect(realArgs[1]).toBe(expArgs.boardState)
              expect(realArgs[2]).toBe(expArgs.cellNum)
            expect(TicTacToe.buttonsEnabled).toBe(false)
            params.success(json[aiMoves])
            expect(TicTacToe.boardState).toBe(json[aiMoves].boardState)
            realArgs = TicTacToe.updateBoardAI.mostRecentCall.args[0]
            expArgs  = json[aiMoves]
            expect(realArgs[0]).toBe("O")
            expect(realArgs[1]).toBe(expArgs[0])
            expect(realArgs[2]).toBe(expArgs[1])
            expect(realArgs[3]).toBe(expArgs[2])
            aiMoves += 1
            if aiMoves < 6
              expect(TicTacToe.buttonsEnabled).toBe(true)
              $("#cell#{humanMoves[aiMoves-1].cellNum} .button").click()),
          0))

    waitsFor((-> (aiMoves == 6)), "should play game", 5000)
    runs( ->
            expect(TicTacToe.initializeGame.calls.length).toBe(1)
            expect(TicTacToe.resetBoard).toHaveBeenCalledWith("X")
            expect(TicTacToe.boardState).toBe("XOXXOOOXX")
            expect($("h1").html()).toMatch("Tie")
            expect($("#board #newGameForm").length).toBe(1))

    $("#newGameButton").click()
