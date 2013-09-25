goog.provide("css_display_spec");
goog.require("cljs.core");
goog.require("domina.events");
goog.require("hiccups.runtime");
goog.require("domina.css");
goog.require("domina");
describe("css-display.reset-board", function() {
  beforeEach(function() {
    var board = affix("#board");
    return domina.set_text_BANG_.call(null, board.affix("#newGameForm"), "a form")
  });
  it("should reset the contents of #board", function() {
    css_display.reset_board.call(null, function(p1__4431_SHARP_) {
      return[cljs.core.str(p1__4431_SHARP_)].join("")
    });
    expect(domina.html.call(null, domina.by_id.call(null, "board"))).toMatch('class\x3d"button"\x3e0\x3c');
    expect(domina.html.call(null, domina.by_id.call(null, "board"))).toMatch('\x3cdiv id\x3d"row2"\x3e');
    return expect(domina.html.call(null, domina.by_id.call(null, "board"))).toMatch('\x3cdiv id\x3d"cell8"\x3e')
  });
  it("should remove form", function() {
    expect(domina.text.call(null, domina.by_id.call(null, "newGameForm"))).toBe("a form");
    css_display.reset_board.call(null, function(p1__4432_SHARP_) {
      return[cljs.core.str(p1__4432_SHARP_)].join("")
    });
    return expect(domina.by_id.call(null, "newGameForm")).toBe(null)
  });
  return it("should attach the given event handler to each button", function() {
    var fun = jasmine.createSpy("fun");
    css_display.reset_board.call(null, fun);
    cljs.core.doall.call(null, function() {
      var iter__3575__auto__ = function iter__4433(s__4434) {
        return new cljs.core.LazySeq(null, false, function() {
          var s__4434__$1 = s__4434;
          while(true) {
            var temp__4092__auto__ = cljs.core.seq.call(null, s__4434__$1);
            if(temp__4092__auto__) {
              var s__4434__$2 = temp__4092__auto__;
              if(cljs.core.chunked_seq_QMARK_.call(null, s__4434__$2)) {
                var c__3573__auto__ = cljs.core.chunk_first.call(null, s__4434__$2);
                var size__3574__auto__ = cljs.core.count.call(null, c__3573__auto__);
                var b__4436 = cljs.core.chunk_buffer.call(null, size__3574__auto__);
                if(function() {
                  var i__4435 = 0;
                  while(true) {
                    if(i__4435 < size__3574__auto__) {
                      var c = cljs.core._nth.call(null, c__3573__auto__, i__4435);
                      cljs.core.chunk_append.call(null, b__4436, domina.events.dispatch_BANG_.call(null, domina.by_id.call(null, [cljs.core.str("cell"), cljs.core.str(c)].join("")), new cljs.core.Keyword(null, "click", "click", 1108654330), cljs.core.PersistentArrayMap.EMPTY));
                      var G__4441 = i__4435 + 1;
                      i__4435 = G__4441;
                      continue
                    }else {
                      return true
                    }
                    break
                  }
                }()) {
                  return cljs.core.chunk_cons.call(null, cljs.core.chunk.call(null, b__4436), iter__4433.call(null, cljs.core.chunk_rest.call(null, s__4434__$2)))
                }else {
                  return cljs.core.chunk_cons.call(null, cljs.core.chunk.call(null, b__4436), null)
                }
              }else {
                var c = cljs.core.first.call(null, s__4434__$2);
                return cljs.core.cons.call(null, domina.events.dispatch_BANG_.call(null, domina.by_id.call(null, [cljs.core.str("cell"), cljs.core.str(c)].join("")), new cljs.core.Keyword(null, "click", "click", 1108654330), cljs.core.PersistentArrayMap.EMPTY), iter__4433.call(null, cljs.core.rest.call(null, s__4434__$2)))
              }
            }else {
              return null
            }
            break
          }
        }, null)
      };
      return iter__3575__auto__.call(null, cljs.core.range.call(null, 0, 9))
    }());
    expect(fun.calls.length).toBe(9);
    return cljs.core.doall.call(null, function() {
      var iter__3575__auto__ = function iter__4437(s__4438) {
        return new cljs.core.LazySeq(null, false, function() {
          var s__4438__$1 = s__4438;
          while(true) {
            var temp__4092__auto__ = cljs.core.seq.call(null, s__4438__$1);
            if(temp__4092__auto__) {
              var s__4438__$2 = temp__4092__auto__;
              if(cljs.core.chunked_seq_QMARK_.call(null, s__4438__$2)) {
                var c__3573__auto__ = cljs.core.chunk_first.call(null, s__4438__$2);
                var size__3574__auto__ = cljs.core.count.call(null, c__3573__auto__);
                var b__4440 = cljs.core.chunk_buffer.call(null, size__3574__auto__);
                if(function() {
                  var i__4439 = 0;
                  while(true) {
                    if(i__4439 < size__3574__auto__) {
                      var c = cljs.core._nth.call(null, c__3573__auto__, i__4439);
                      cljs.core.chunk_append.call(null, b__4440, expect(fun).toHaveBeenCalledWith(c));
                      var G__4442 = i__4439 + 1;
                      i__4439 = G__4442;
                      continue
                    }else {
                      return true
                    }
                    break
                  }
                }()) {
                  return cljs.core.chunk_cons.call(null, cljs.core.chunk.call(null, b__4440), iter__4437.call(null, cljs.core.chunk_rest.call(null, s__4438__$2)))
                }else {
                  return cljs.core.chunk_cons.call(null, cljs.core.chunk.call(null, b__4440), null)
                }
              }else {
                var c = cljs.core.first.call(null, s__4438__$2);
                return cljs.core.cons.call(null, expect(fun).toHaveBeenCalledWith(c), iter__4437.call(null, cljs.core.rest.call(null, s__4438__$2)))
              }
            }else {
              return null
            }
            break
          }
        }, null)
      };
      return iter__3575__auto__.call(null, cljs.core.range.call(null, 0, 9))
    }())
  })
});
describe("css-display.display-form", function() {
  beforeEach(function() {
    var board = affix("#board");
    return domina.set_text_BANG_.call(null, board.affix("#fakeBoard"), "a board")
  });
  it("should display the form", function() {
    css_display.display_form.call(null, function() {
      return cljs.core.List.EMPTY
    });
    expect(domina.html.call(null, domina.by_id.call(null, "newGameForm"))).toMatch('\x3cinput checked\x3d"checked" name\x3d"marker" value\x3d"X" type\x3d"radio"\x3eX');
    expect(domina.html.call(null, domina.by_id.call(null, "newGameForm"))).toMatch('\x3cinput checked\x3d"checked" name\x3d"move" value\x3d"0" type\x3d"radio"\x3eFirst');
    return expect(domina.html.call(null, domina.by_id.call(null, "newGameForm"))).toMatch('\x3cbutton id\x3d"newGameButton" type\x3d"button"\x3ePlay!\x3c/button\x3e')
  });
  it("should not remove other board elements", function() {
    css_display.display_form.call(null, function() {
      return cljs.core.List.EMPTY
    });
    return expect(domina.text.call(null, domina.by_id.call(null, "fakeBoard"))).toBe("a board")
  });
  return it("should attach the given function to the button", function() {
    var fun = jasmine.createSpy("fun");
    css_display.display_form.call(null, fun);
    domina.events.dispatch_BANG_.call(null, domina.by_id.call(null, "newGameButton"), new cljs.core.Keyword(null, "click", "click", 1108654330), cljs.core.PersistentArrayMap.EMPTY);
    return expect(fun.calls.length).toBe(1)
  })
});
describe("css-display.get-cell", function() {
  beforeEach(function() {
    var board = affix("#board");
    domina.set_text_BANG_.call(null, board.affix("#cell0"), "cell 0");
    return domina.set_text_BANG_.call(null, board.affix("#cell3"), "cell 3")
  });
  it("should get the given cell by number", function() {
    expect(domina.text.call(null, css_display.get_cell.call(null, 0))).toBe("cell 0");
    return expect(domina.text.call(null, css_display.get_cell.call(null, 3))).toBe("cell 3")
  });
  return it("should return null for invalid cell", function() {
    return expect(css_display.get_cell.call(null, -1)).toBe(null)
  })
});
describe("css-display.get-button", function() {
  beforeEach(function() {
    var board = affix("#board");
    domina.set_text_BANG_.call(null, board.affix("#cell0").affix(".button"), "btn 0");
    return domina.set_text_BANG_.call(null, board.affix("#cell3").affix(".button"), "btn 3")
  });
  it("should get the given button by number", function() {
    expect(domina.text.call(null, css_display.get_button.call(null, 0))).toBe("btn 0");
    return expect(domina.text.call(null, css_display.get_button.call(null, 3))).toBe("btn 3")
  });
  return it("should return null for invalid cell", function() {
    return expect(css_display.get_button.call(null, -1)).toBe(null)
  })
});
describe("css-display.make-move", function() {
  beforeEach(function() {
    var board = affix("#board");
    domina.set_text_BANG_.call(null, board.affix("#cell0"), "empty cell");
    return domina.set_text_BANG_.call(null, board.affix("#cell5"), "empty cell")
  });
  return it("should replace cell contents with marker", function() {
    css_display.make_move.call(null, "X", 0);
    expect(domina.html.call(null, domina.by_id.call(null, "cell0"))).toBe("X");
    css_display.make_move.call(null, "O", 5);
    return expect(domina.html.call(null, domina.by_id.call(null, "cell5"))).toBe("O")
  })
});
describe("css-display.get-marker", function() {
  beforeEach(function() {
    var f = affix("#newGameForm");
    f.affix('input[id\x3d"x"][name\x3d"marker"][type\x3d"radio"][value\x3d"X"]');
    return f.affix('input[id\x3d"o"][name\x3d"marker"][type\x3d"radio"][value\x3d"O"]')
  });
  return it("should return the marker value from the form", function() {
    domina.set_attr_BANG_.call(null, domina.by_id.call(null, "x"), new cljs.core.Keyword(null, "checked", "checked", 1756218137), true);
    expect(css_display.get_marker.call(null)).toBe("X");
    domina.set_attr_BANG_.call(null, domina.by_id.call(null, "o"), new cljs.core.Keyword(null, "checked", "checked", 1756218137), true);
    return expect(css_display.get_marker.call(null)).toBe("O")
  })
});
describe("css-display.get-move", function() {
  beforeEach(function() {
    var f = affix("#newGameForm");
    f.affix('input[id\x3d"zero"][name\x3d"move"][type\x3d"radio"][value\x3d"0"]');
    return f.affix('input[id\x3d"one"][name\x3d"move"][type\x3d"radio"][value\x3d"1"]')
  });
  return it("should return the move value from the form", function() {
    domina.set_attr_BANG_.call(null, domina.by_id.call(null, "zero"), new cljs.core.Keyword(null, "checked", "checked", 1756218137), true);
    expect(css_display.get_move.call(null)).toBe("0");
    domina.set_attr_BANG_.call(null, domina.by_id.call(null, "one"), new cljs.core.Keyword(null, "checked", "checked", 1756218137), true);
    return expect(css_display.get_move.call(null)).toBe("1")
  })
});
describe("css-display end of game messages", function() {
  beforeEach(function() {
    return affix("#board")
  });
  it("should display a win message", function() {
    css_display.display_win_message.call(null);
    return expect(domina.text.call(null, domina.css.sel.call(null, "#board h1"))).toMatch("Win")
  });
  it("should display a lose message", function() {
    css_display.display_lose_message.call(null);
    return expect(domina.text.call(null, domina.css.sel.call(null, "#board h1"))).toMatch("Lose")
  });
  return it("should display a tie message", function() {
    css_display.display_tie_message.call(null);
    return expect(domina.text.call(null, domina.css.sel.call(null, "#board h1"))).toMatch("Tie")
  })
});
