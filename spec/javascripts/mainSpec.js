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
    css_display.reset_board.call(null, function(p1__12274_SHARP_) {
      return[cljs.core.str(p1__12274_SHARP_)].join("")
    });
    expect(domina.html.call(null, domina.by_id.call(null, "board"))).toMatch('class\x3d"button"\x3e0\x3c');
    expect(domina.html.call(null, domina.by_id.call(null, "board"))).toMatch('\x3cdiv id\x3d"row2"\x3e');
    return expect(domina.html.call(null, domina.by_id.call(null, "board"))).toMatch('\x3cdiv id\x3d"cell8"\x3e')
  });
  it("should remove form", function() {
    expect(domina.text.call(null, domina.by_id.call(null, "newGameForm"))).toBe("a form");
    css_display.reset_board.call(null, function(p1__12275_SHARP_) {
      return[cljs.core.str(p1__12275_SHARP_)].join("")
    });
    return expect(domina.by_id.call(null, "newGameForm")).toBe(null)
  });
  return it("should attach the given event handler to each button", function() {
    var fun = jasmine.createSpy("fun");
    css_display.reset_board.call(null, fun);
    var seq__12276_12284 = cljs.core.seq.call(null, cljs.core.range.call(null, 0, 9));
    var chunk__12277_12285 = null;
    var count__12278_12286 = 0;
    var i__12279_12287 = 0;
    while(true) {
      if(i__12279_12287 < count__12278_12286) {
        var c_12288 = cljs.core._nth.call(null, chunk__12277_12285, i__12279_12287);
        domina.events.dispatch_BANG_.call(null, domina.by_id.call(null, [cljs.core.str("cell"), cljs.core.str(c_12288)].join("")), new cljs.core.Keyword(null, "click", "click", 1108654330), cljs.core.PersistentArrayMap.EMPTY);
        var G__12289 = seq__12276_12284;
        var G__12290 = chunk__12277_12285;
        var G__12291 = count__12278_12286;
        var G__12292 = i__12279_12287 + 1;
        seq__12276_12284 = G__12289;
        chunk__12277_12285 = G__12290;
        count__12278_12286 = G__12291;
        i__12279_12287 = G__12292;
        continue
      }else {
        var temp__4092__auto___12293 = cljs.core.seq.call(null, seq__12276_12284);
        if(temp__4092__auto___12293) {
          var seq__12276_12294__$1 = temp__4092__auto___12293;
          if(cljs.core.chunked_seq_QMARK_.call(null, seq__12276_12294__$1)) {
            var c__3606__auto___12295 = cljs.core.chunk_first.call(null, seq__12276_12294__$1);
            var G__12296 = cljs.core.chunk_rest.call(null, seq__12276_12294__$1);
            var G__12297 = c__3606__auto___12295;
            var G__12298 = cljs.core.count.call(null, c__3606__auto___12295);
            var G__12299 = 0;
            seq__12276_12284 = G__12296;
            chunk__12277_12285 = G__12297;
            count__12278_12286 = G__12298;
            i__12279_12287 = G__12299;
            continue
          }else {
            var c_12300 = cljs.core.first.call(null, seq__12276_12294__$1);
            domina.events.dispatch_BANG_.call(null, domina.by_id.call(null, [cljs.core.str("cell"), cljs.core.str(c_12300)].join("")), new cljs.core.Keyword(null, "click", "click", 1108654330), cljs.core.PersistentArrayMap.EMPTY);
            var G__12301 = cljs.core.next.call(null, seq__12276_12294__$1);
            var G__12302 = null;
            var G__12303 = 0;
            var G__12304 = 0;
            seq__12276_12284 = G__12301;
            chunk__12277_12285 = G__12302;
            count__12278_12286 = G__12303;
            i__12279_12287 = G__12304;
            continue
          }
        }else {
        }
      }
      break
    }
    expect(fun.calls.length).toBe(9);
    var seq__12280 = cljs.core.seq.call(null, cljs.core.range.call(null, 0, 9));
    var chunk__12281 = null;
    var count__12282 = 0;
    var i__12283 = 0;
    while(true) {
      if(i__12283 < count__12282) {
        var c = cljs.core._nth.call(null, chunk__12281, i__12283);
        expect(fun).toHaveBeenCalledWith(c);
        var G__12305 = seq__12280;
        var G__12306 = chunk__12281;
        var G__12307 = count__12282;
        var G__12308 = i__12283 + 1;
        seq__12280 = G__12305;
        chunk__12281 = G__12306;
        count__12282 = G__12307;
        i__12283 = G__12308;
        continue
      }else {
        var temp__4092__auto__ = cljs.core.seq.call(null, seq__12280);
        if(temp__4092__auto__) {
          var seq__12280__$1 = temp__4092__auto__;
          if(cljs.core.chunked_seq_QMARK_.call(null, seq__12280__$1)) {
            var c__3606__auto__ = cljs.core.chunk_first.call(null, seq__12280__$1);
            var G__12309 = cljs.core.chunk_rest.call(null, seq__12280__$1);
            var G__12310 = c__3606__auto__;
            var G__12311 = cljs.core.count.call(null, c__3606__auto__);
            var G__12312 = 0;
            seq__12280 = G__12309;
            chunk__12281 = G__12310;
            count__12282 = G__12311;
            i__12283 = G__12312;
            continue
          }else {
            var c = cljs.core.first.call(null, seq__12280__$1);
            expect(fun).toHaveBeenCalledWith(c);
            var G__12313 = cljs.core.next.call(null, seq__12280__$1);
            var G__12314 = null;
            var G__12315 = 0;
            var G__12316 = 0;
            seq__12280 = G__12313;
            chunk__12281 = G__12314;
            count__12282 = G__12315;
            i__12283 = G__12316;
            continue
          }
        }else {
          return null
        }
      }
      break
    }
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
goog.provide("tic_tac_toe_spec");
goog.require("cljs.core");
goog.require("tic_tac_toe.display");
describe("tic-tac-toe.reset-board", function() {
  beforeEach(function() {
    return affix("#board")
  });
  it("should reset tic-tac-toe.board-state to an empty board", function() {
    cljs.core.reset_BANG_.call(null, tic_tac_toe.board_state, "blahblahblah");
    tic_tac_toe.reset_board.call(null, "X");
    return expect(cljs.core.deref.call(null, tic_tac_toe.board_state)).toBe("_________")
  });
  it("should reset tic-tac-toe.buttons-enabled to false", function() {
    cljs.core.reset_BANG_.call(null, tic_tac_toe.buttons_enabled, true);
    tic_tac_toe.reset_board.call(null, "X");
    return expect(cljs.core.deref.call(null, tic_tac_toe.buttons_enabled)).toBe(false)
  });
  return it("should pass event handler to display.reset-board which calls make-move", function() {
    var mm_args = cljs.core.atom.call(null, null);
    var rb_args = cljs.core.atom.call(null, null);
    var make_move3999 = tic_tac_toe.make_move;
    var reset_board4000 = tic_tac_toe.display.reset_board;
    try {
      tic_tac_toe.make_move = function(a, b, c) {
        return cljs.core.reset_BANG_.call(null, mm_args, cljs.core.PersistentVector.fromArray([a, b, c], true))
      };
      tic_tac_toe.display.reset_board = function(f) {
        return cljs.core.reset_BANG_.call(null, rb_args, f)
      };
      tic_tac_toe.reset_board.call(null, "X");
      return alert([cljs.core.str(cljs.core.deref.call(null, rb_args))].join(""))
    }finally {
      tic_tac_toe.display.reset_board = reset_board4000;
      tic_tac_toe.make_move = make_move3999
    }
  })
});
