describe "CSSDisplay.resetBoard", ->
  beforeEach ->
    board = affix("#board")
    board.affix("#newGameForm").html("a form")

  it "should reset the contents of #board", ->
    CSSDisplay.resetBoard "X"
    expect($("div#board").html()).toMatch("class=\"button\">0<")
    expect($("div#board").html()).toMatch("<div id=\"row2\">")
    expect($("div#board").html()).toMatch("<div id=\"cell8\">")

  it "should remove form", ->
    expect($("#newGameForm").html()).toBe("a form")
    CSSDisplay.resetBoard "X"
    expect($("#newGameForm").size()).toBe(0)

  it "should attach the given event handler to each button", ->
    fn = jasmine.createSpy("fn")
    CSSDisplay.resetBoard(fn)
    for cell in [0..8]
      $("#cell#{cell}").click()
    expect(fn.calls.length).toBe(9)
    for cell in [0..8]
      expect(fn).toHaveBeenCalledWith(cell)

describe "CSSDisplay.getCell", ->
  beforeEach ->
    board = affix("#board")
    board.affix("#cell0").html("0")
    board.affix("#cell1").html("1")
    board.affix("#cell2").html("2")

  it "should get the given cell by number", ->
    expect(CSSDisplay.getCell(0).html()).toBe("0")
    expect(CSSDisplay.getCell(2).html()).toBe("2")

  it "should return an empty element set for a nonexistent cell", ->
    expect(CSSDisplay.getCell(-1).size()).toBe(0)
    expect(CSSDisplay.getCell(9).size()).toBe(0)

describe "CSSDisplay.getButton", ->
  beforeEach ->
    board = affix("#board")
    board.affix("#cell0").affix(".button").html("0")
    board.affix("#cell1").affix(".button").html("1")
    board.affix("#cell2").affix(".button").html("2")

  it "should get the given cell by number", ->
    expect(CSSDisplay.getButton(0).html()).toBe("0")
    expect(CSSDisplay.getButton(2).html()).toBe("2")

  it "should return an empty element set for a nonexistent cell", ->
    expect(CSSDisplay.getButton(-1).size()).toBe(0)
    expect(CSSDisplay.getButton(9).size()).toBe(0)

describe "CSSDisplay.makeMove", ->
  beforeEach ->
    board = affix("#board")
    board.affix("#cell0").html("empty cell")
    board.affix("#cell5").html("empty cell")

  it "should replace cell contents with marker", ->
    CSSDisplay.makeMove("X", 0)
    CSSDisplay.makeMove("O", 5)
    expect($("#cell0").html()).toBe("X")
    expect($("#cell5").html()).toBe("O")

describe "CSSDisplay.getMarker", ->
  x = o = null

  beforeEach ->
    form = affix("#newGameForm")
    x = form.affix('input[name="marker"][type="radio"][value="X"]')
    o = form.affix('input[name="marker"][type="radio"][value="O"]')

  it "should return the marker value from the form", ->
    x.prop("checked", true)
    expect(CSSDisplay.getMarker()).toBe("X")
    o.prop("checked", true)
    expect(CSSDisplay.getMarker()).toBe("O")

describe "CSSDisplay.getMove", ->
  m0 = m1 = null

  beforeEach ->
    form = affix("#newGameForm")
    m0 = form.affix('input[name="move"][type="radio"][value="0"]')
    m1 = form.affix('input[name="move"][type="radio"][value="1"]')

  it "should return the move value from the form", ->
    m0.prop("checked", true)
    expect(CSSDisplay.getMove()).toBe("0")
    m1.prop("checked", true)
    expect(CSSDisplay.getMove()).toBe("1")

describe "CSSDisplay end of game messages", ->
  
  beforeEach ->
    affix("#board")

  it "should display a win message", ->
    CSSDisplay.displayWinMessage()
    expect($("#board h1").html()).toMatch("Win")

  it "should display a lose message", ->
    CSSDisplay.displayLoseMessage()
    expect($("#board h1").html()).toMatch("Lose")

  it "should display a tie message", ->
    CSSDisplay.displayTieMessage()
    expect($("#board h1").html()).toMatch("Tie")

describe "CSSDisplay.displayForm", ->

  beforeEach ->
    affix("#board")

  it "should display the newGameForm with button", ->
    CSSDisplay.displayForm(-> "fn called")
    expect($("#board #newGameForm #newGameButton").size()).toBe(1)

  it "should attach the given fn to the newGameButton element", ->
    fn = jasmine.createSpy("fn")
    CSSDisplay.displayForm(fn)
    expect(fn).not.toHaveBeenCalled()
    $("#newGameButton").click()
    expect(fn).toHaveBeenCalled()
