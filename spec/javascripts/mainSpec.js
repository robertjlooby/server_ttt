goog.provide("CSSDisplaySpec");
goog.require("cljs.core");
goog.require("domina.events");
goog.require("hiccups.runtime");
goog.require("domina");
describe("CSSDisplay.resetBoard", function() {
  beforeEach(function() {
    var board = affix("#board");
    return domina.set_text_BANG_.call(null, board.affix("#newGameForm"), "a form")
  });
  it("should reset the contents of #board", function() {
    CSSDisplay.resetBoard.call(null, function(p1__30250_SHARP_) {
      return[cljs.core.str(p1__30250_SHARP_)].join("")
    });
    expect(domina.html.call(null, domina.by_id.call(null, "board"))).toMatch('class\x3d"button"\x3e0\x3c');
    expect(domina.html.call(null, domina.by_id.call(null, "board"))).toMatch('\x3cdiv id\x3d"row2"\x3e');
    return expect(domina.html.call(null, domina.by_id.call(null, "board"))).toMatch('\x3cdiv id\x3d"cell8"\x3e')
  });
  it("should remove form", function() {
    expect(domina.text.call(null, domina.by_id.call(null, "newGameForm"))).toBe("a form");
    CSSDisplay.resetBoard.call(null, function(p1__30251_SHARP_) {
      return[cljs.core.str(p1__30251_SHARP_)].join("")
    });
    return expect(domina.by_id.call(null, "newGameForm")).toBe(null)
  });
  return it("should attach the given event handler to each button", function() {
    var fun = jasmine.createSpy("fun");
    CSSDisplay.resetBoard.call(null, fun);
    cljs.core.doall.call(null, function() {
      var iter__3530__auto__ = function iter__30252(s__30253) {
        return new cljs.core.LazySeq(null, false, function() {
          var s__30253__$1 = s__30253;
          while(true) {
            var temp__4092__auto__ = cljs.core.seq.call(null, s__30253__$1);
            if(temp__4092__auto__) {
              var s__30253__$2 = temp__4092__auto__;
              if(cljs.core.chunked_seq_QMARK_.call(null, s__30253__$2)) {
                var c__3528__auto__ = cljs.core.chunk_first.call(null, s__30253__$2);
                var size__3529__auto__ = cljs.core.count.call(null, c__3528__auto__);
                var b__30255 = cljs.core.chunk_buffer.call(null, size__3529__auto__);
                if(function() {
                  var i__30254 = 0;
                  while(true) {
                    if(i__30254 < size__3529__auto__) {
                      var c = cljs.core._nth.call(null, c__3528__auto__, i__30254);
                      cljs.core.chunk_append.call(null, b__30255, domina.events.dispatch_BANG_.call(null, domina.by_id.call(null, [cljs.core.str("cell"), cljs.core.str(c)].join("")), new cljs.core.Keyword(null, "click", "click", 1108654330), cljs.core.PersistentArrayMap.EMPTY));
                      var G__30260 = i__30254 + 1;
                      i__30254 = G__30260;
                      continue
                    }else {
                      return true
                    }
                    break
                  }
                }()) {
                  return cljs.core.chunk_cons.call(null, cljs.core.chunk.call(null, b__30255), iter__30252.call(null, cljs.core.chunk_rest.call(null, s__30253__$2)))
                }else {
                  return cljs.core.chunk_cons.call(null, cljs.core.chunk.call(null, b__30255), null)
                }
              }else {
                var c = cljs.core.first.call(null, s__30253__$2);
                return cljs.core.cons.call(null, domina.events.dispatch_BANG_.call(null, domina.by_id.call(null, [cljs.core.str("cell"), cljs.core.str(c)].join("")), new cljs.core.Keyword(null, "click", "click", 1108654330), cljs.core.PersistentArrayMap.EMPTY), iter__30252.call(null, cljs.core.rest.call(null, s__30253__$2)))
              }
            }else {
              return null
            }
            break
          }
        }, null)
      };
      return iter__3530__auto__.call(null, cljs.core.range.call(null, 0, 9))
    }());
    expect(fun.calls.length).toBe(9);
    return cljs.core.doall.call(null, function() {
      var iter__3530__auto__ = function iter__30256(s__30257) {
        return new cljs.core.LazySeq(null, false, function() {
          var s__30257__$1 = s__30257;
          while(true) {
            var temp__4092__auto__ = cljs.core.seq.call(null, s__30257__$1);
            if(temp__4092__auto__) {
              var s__30257__$2 = temp__4092__auto__;
              if(cljs.core.chunked_seq_QMARK_.call(null, s__30257__$2)) {
                var c__3528__auto__ = cljs.core.chunk_first.call(null, s__30257__$2);
                var size__3529__auto__ = cljs.core.count.call(null, c__3528__auto__);
                var b__30259 = cljs.core.chunk_buffer.call(null, size__3529__auto__);
                if(function() {
                  var i__30258 = 0;
                  while(true) {
                    if(i__30258 < size__3529__auto__) {
                      var c = cljs.core._nth.call(null, c__3528__auto__, i__30258);
                      cljs.core.chunk_append.call(null, b__30259, expect(fun).toHaveBeenCalledWith(c));
                      var G__30261 = i__30258 + 1;
                      i__30258 = G__30261;
                      continue
                    }else {
                      return true
                    }
                    break
                  }
                }()) {
                  return cljs.core.chunk_cons.call(null, cljs.core.chunk.call(null, b__30259), iter__30256.call(null, cljs.core.chunk_rest.call(null, s__30257__$2)))
                }else {
                  return cljs.core.chunk_cons.call(null, cljs.core.chunk.call(null, b__30259), null)
                }
              }else {
                var c = cljs.core.first.call(null, s__30257__$2);
                return cljs.core.cons.call(null, expect(fun).toHaveBeenCalledWith(c), iter__30256.call(null, cljs.core.rest.call(null, s__30257__$2)))
              }
            }else {
              return null
            }
            break
          }
        }, null)
      };
      return iter__3530__auto__.call(null, cljs.core.range.call(null, 0, 9))
    }())
  })
});
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
describe("CSSDisplay.makeMove", function() {
  beforeEach(function() {
    var board = affix("#board");
    domina.set_text_BANG_.call(null, board.affix("#cell0"), "empty cell");
    return domina.set_text_BANG_.call(null, board.affix("#cell5"), "empty cell")
  });
  return it("should replace cell contents with marker", function() {
    CSSDisplay.makeMove.call(null, "X", 0);
    expect(domina.html.call(null, domina.by_id.call(null, "cell0"))).toBe("X");
    CSSDisplay.makeMove.call(null, "O", 5);
    return expect(domina.html.call(null, domina.by_id.call(null, "cell5"))).toBe("O")
  })
});
describe("CSSDisplay.getMarker", function() {
  beforeEach(function() {
    var f = affix("#newGameForm");
    f.affix('input[id\x3d"x"][name\x3d"marker"][type\x3d"radio"][value\x3d"X"]');
    return f.affix('input[id\x3d"o"][name\x3d"marker"][type\x3d"radio"][value\x3d"O"]')
  });
  return it("should return the marker value from the form", function() {
    domina.set_attr_BANG_.call(null, domina.by_id.call(null, "x"), new cljs.core.Keyword(null, "checked", "checked", 1756218137), true);
    expect(CSSDisplay.getMarker.call(null)).toBe("X");
    domina.set_attr_BANG_.call(null, domina.by_id.call(null, "o"), new cljs.core.Keyword(null, "checked", "checked", 1756218137), true);
    return expect(CSSDisplay.getMarker.call(null)).toBe("O")
  })
});
describe("CSSDisplay.getMove", function() {
  beforeEach(function() {
    var f = affix("#newGameForm");
    f.affix('input[id\x3d"zero"][name\x3d"move"][type\x3d"radio"][value\x3d"0"]');
    return f.affix('input[id\x3d"one"][name\x3d"move"][type\x3d"radio"][value\x3d"1"]')
  });
  return it("should return the move value from the form", function() {
    domina.set_attr_BANG_.call(null, domina.by_id.call(null, "zero"), new cljs.core.Keyword(null, "checked", "checked", 1756218137), true);
    expect(CSSDisplay.getMove.call(null)).toBe("0");
    domina.set_attr_BANG_.call(null, domina.by_id.call(null, "one"), new cljs.core.Keyword(null, "checked", "checked", 1756218137), true);
    return expect(CSSDisplay.getMove.call(null)).toBe("1")
  })
});
