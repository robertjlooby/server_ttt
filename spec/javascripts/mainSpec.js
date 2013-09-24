goog.provide("CSSDisplaySpec");
goog.require("cljs.core");
goog.require("domina.events");
goog.require("hiccups.runtime");
goog.require("domina");
describe("CSSDisplay.getCell", function() {
  beforeEach(function() {
    var board = affix("#board");
    domina.set_text_BANG_.call(null, board.affix("#cell0"), "cell 0");
    return domina.set_text_BANG_.call(null, board.affix("#cell3"), "cell 3")
  });
  it("should get the given cell by number", function() {
    expect(domina.text.call(null, CSSDisplay.getCell.call(null, 0))).toBe("cell 0");
    return expect(domina.text.call(null, CSSDisplay.getCell.call(null, 3))).toBe("cell 3")
  });
  return it("should return null for invalid cell", function() {
    return expect(CSSDisplay.getCell.call(null, -1)).toBe(null)
  })
});
describe("CSSDisplay.getButton", function() {
  beforeEach(function() {
    var board = affix("#board");
    domina.set_text_BANG_.call(null, board.affix("#cell0").affix(".button"), "btn 0");
    return domina.set_text_BANG_.call(null, board.affix("#cell3").affix(".button"), "btn 3")
  });
  it("should get the given button by number", function() {
    expect(domina.text.call(null, CSSDisplay.getButton.call(null, 0))).toBe("btn 0");
    return expect(domina.text.call(null, CSSDisplay.getButton.call(null, 3))).toBe("btn 3")
  });
  return it("should return null for invalid cell", function() {
    return expect(CSSDisplay.getButton.call(null, -1)).toBe(null)
  })
});
