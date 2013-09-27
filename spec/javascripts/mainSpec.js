goog.provide("css_display_spec");
goog.require("cljs.core");
goog.require("domina.events");
goog.require("hiccups.runtime");
goog.require("domina.css");
goog.require("domina");
goog.require("abstract_display");
describe("css-display.reset-board", function() {
  beforeEach(function() {
    var board = affix("#board");
    return domina.set_text_BANG_.call(null, board.affix("#newGameForm"), "a form")
  });
  it("should reset the contents of #board", function() {
    var d_5787 = new display.css_display;
    abstract_display.reset_board.call(null, d_5787, function(p1__5777_SHARP_) {
      return[cljs.core.str(p1__5777_SHARP_)].join("")
    });
    expect(domina.html.call(null, domina.by_id.call(null, "board"))).toMatch('class\x3d"button"\x3e0\x3c');
    expect(domina.html.call(null, domina.by_id.call(null, "board"))).toMatch('\x3cdiv id\x3d"row2"\x3e');
    return expect(domina.html.call(null, domina.by_id.call(null, "board"))).toMatch('\x3cdiv id\x3d"cell8"\x3e')
  });
  it("should remove form", function() {
    expect(domina.text.call(null, domina.by_id.call(null, "newGameForm"))).toBe("a form");
    var d_5788 = new display.css_display;
    abstract_display.reset_board.call(null, d_5788, function(p1__5778_SHARP_) {
      return[cljs.core.str(p1__5778_SHARP_)].join("")
    });
    return expect(domina.by_id.call(null, "newGameForm")).toBe(null)
  });
  return it("should attach the given event handler to each button", function() {
    var fun = jasmine.createSpy("fun");
    var d_5789 = new display.css_display;
    abstract_display.reset_board.call(null, d_5789, fun);
    var seq__5779_5790 = cljs.core.seq.call(null, cljs.core.range.call(null, 0, 9));
    var chunk__5780_5791 = null;
    var count__5781_5792 = 0;
    var i__5782_5793 = 0;
    while(true) {
      if(i__5782_5793 < count__5781_5792) {
        var c_5794 = cljs.core._nth.call(null, chunk__5780_5791, i__5782_5793);
        domina.events.dispatch_BANG_.call(null, domina.by_id.call(null, [cljs.core.str("cell"), cljs.core.str(c_5794)].join("")), new cljs.core.Keyword(null, "click", "click", 1108654330), cljs.core.PersistentArrayMap.EMPTY);
        var G__5795 = seq__5779_5790;
        var G__5796 = chunk__5780_5791;
        var G__5797 = count__5781_5792;
        var G__5798 = i__5782_5793 + 1;
        seq__5779_5790 = G__5795;
        chunk__5780_5791 = G__5796;
        count__5781_5792 = G__5797;
        i__5782_5793 = G__5798;
        continue
      }else {
        var temp__4092__auto___5799 = cljs.core.seq.call(null, seq__5779_5790);
        if(temp__4092__auto___5799) {
          var seq__5779_5800__$1 = temp__4092__auto___5799;
          if(cljs.core.chunked_seq_QMARK_.call(null, seq__5779_5800__$1)) {
            var c__3561__auto___5801 = cljs.core.chunk_first.call(null, seq__5779_5800__$1);
            var G__5802 = cljs.core.chunk_rest.call(null, seq__5779_5800__$1);
            var G__5803 = c__3561__auto___5801;
            var G__5804 = cljs.core.count.call(null, c__3561__auto___5801);
            var G__5805 = 0;
            seq__5779_5790 = G__5802;
            chunk__5780_5791 = G__5803;
            count__5781_5792 = G__5804;
            i__5782_5793 = G__5805;
            continue
          }else {
            var c_5806 = cljs.core.first.call(null, seq__5779_5800__$1);
            domina.events.dispatch_BANG_.call(null, domina.by_id.call(null, [cljs.core.str("cell"), cljs.core.str(c_5806)].join("")), new cljs.core.Keyword(null, "click", "click", 1108654330), cljs.core.PersistentArrayMap.EMPTY);
            var G__5807 = cljs.core.next.call(null, seq__5779_5800__$1);
            var G__5808 = null;
            var G__5809 = 0;
            var G__5810 = 0;
            seq__5779_5790 = G__5807;
            chunk__5780_5791 = G__5808;
            count__5781_5792 = G__5809;
            i__5782_5793 = G__5810;
            continue
          }
        }else {
        }
      }
      break
    }
    expect(fun.calls.length).toBe(9);
    var seq__5783 = cljs.core.seq.call(null, cljs.core.range.call(null, 0, 9));
    var chunk__5784 = null;
    var count__5785 = 0;
    var i__5786 = 0;
    while(true) {
      if(i__5786 < count__5785) {
        var c = cljs.core._nth.call(null, chunk__5784, i__5786);
        expect(fun).toHaveBeenCalledWith(c);
        var G__5811 = seq__5783;
        var G__5812 = chunk__5784;
        var G__5813 = count__5785;
        var G__5814 = i__5786 + 1;
        seq__5783 = G__5811;
        chunk__5784 = G__5812;
        count__5785 = G__5813;
        i__5786 = G__5814;
        continue
      }else {
        var temp__4092__auto__ = cljs.core.seq.call(null, seq__5783);
        if(temp__4092__auto__) {
          var seq__5783__$1 = temp__4092__auto__;
          if(cljs.core.chunked_seq_QMARK_.call(null, seq__5783__$1)) {
            var c__3561__auto__ = cljs.core.chunk_first.call(null, seq__5783__$1);
            var G__5815 = cljs.core.chunk_rest.call(null, seq__5783__$1);
            var G__5816 = c__3561__auto__;
            var G__5817 = cljs.core.count.call(null, c__3561__auto__);
            var G__5818 = 0;
            seq__5783 = G__5815;
            chunk__5784 = G__5816;
            count__5785 = G__5817;
            i__5786 = G__5818;
            continue
          }else {
            var c = cljs.core.first.call(null, seq__5783__$1);
            expect(fun).toHaveBeenCalledWith(c);
            var G__5819 = cljs.core.next.call(null, seq__5783__$1);
            var G__5820 = null;
            var G__5821 = 0;
            var G__5822 = 0;
            seq__5783 = G__5819;
            chunk__5784 = G__5820;
            count__5785 = G__5821;
            i__5786 = G__5822;
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
    var d_5823 = new display.css_display;
    abstract_display.display_form.call(null, d_5823, function() {
      return cljs.core.List.EMPTY
    });
    expect(domina.html.call(null, domina.by_id.call(null, "newGameForm"))).toMatch('\x3cinput checked\x3d"checked" name\x3d"marker" value\x3d"X" type\x3d"radio"\x3eX');
    expect(domina.html.call(null, domina.by_id.call(null, "newGameForm"))).toMatch('\x3cinput checked\x3d"checked" name\x3d"move" value\x3d"0" type\x3d"radio"\x3eFirst');
    return expect(domina.html.call(null, domina.by_id.call(null, "newGameForm"))).toMatch('\x3cbutton id\x3d"newGameButton" type\x3d"button"\x3ePlay!\x3c/button\x3e')
  });
  it("should not remove other board elements", function() {
    var d_5824 = new display.css_display;
    abstract_display.display_form.call(null, d_5824, function() {
      return cljs.core.List.EMPTY
    });
    return expect(domina.text.call(null, domina.by_id.call(null, "fakeBoard"))).toBe("a board")
  });
  return it("should attach the given function to the button", function() {
    var fun = jasmine.createSpy("fun");
    var d_5825 = new display.css_display;
    abstract_display.display_form.call(null, d_5825, fun);
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
    var d = new display.css_display;
    expect(domina.text.call(null, abstract_display.get_cell.call(null, d, 0))).toBe("cell 0");
    return expect(domina.text.call(null, abstract_display.get_cell.call(null, d, 3))).toBe("cell 3")
  });
  return it("should return null for invalid cell", function() {
    var d = new display.css_display;
    return expect(abstract_display.get_cell.call(null, d, -1)).toBe(null)
  })
});
describe("css-display.get-button", function() {
  beforeEach(function() {
    var board = affix("#board");
    domina.set_text_BANG_.call(null, board.affix("#cell0").affix(".button"), "btn 0");
    return domina.set_text_BANG_.call(null, board.affix("#cell3").affix(".button"), "btn 3")
  });
  it("should get the given button by number", function() {
    var d = new display.css_display;
    expect(domina.text.call(null, abstract_display.get_button.call(null, d, 0))).toBe("btn 0");
    return expect(domina.text.call(null, abstract_display.get_button.call(null, d, 3))).toBe("btn 3")
  });
  return it("should return null for invalid cell", function() {
    var d = new display.css_display;
    return expect(abstract_display.get_button.call(null, d, -1)).toBe(null)
  })
});
describe("css-display.make-move", function() {
  beforeEach(function() {
    var board = affix("#board");
    domina.set_text_BANG_.call(null, board.affix("#cell0"), "empty cell");
    return domina.set_text_BANG_.call(null, board.affix("#cell5"), "empty cell")
  });
  return it("should replace cell contents with marker", function() {
    var d = new display.css_display;
    abstract_display.make_move.call(null, d, "X", 0);
    expect(domina.html.call(null, domina.by_id.call(null, "cell0"))).toBe("X");
    abstract_display.make_move.call(null, d, "O", 5);
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
    var d = new display.css_display;
    domina.set_attr_BANG_.call(null, domina.by_id.call(null, "x"), new cljs.core.Keyword(null, "checked", "checked", 1756218137), true);
    expect(abstract_display.get_marker.call(null, d)).toBe("X");
    domina.set_attr_BANG_.call(null, domina.by_id.call(null, "o"), new cljs.core.Keyword(null, "checked", "checked", 1756218137), true);
    return expect(abstract_display.get_marker.call(null, d)).toBe("O")
  })
});
describe("css-display.get-move", function() {
  beforeEach(function() {
    var f = affix("#newGameForm");
    f.affix('input[id\x3d"zero"][name\x3d"move"][type\x3d"radio"][value\x3d"0"]');
    return f.affix('input[id\x3d"one"][name\x3d"move"][type\x3d"radio"][value\x3d"1"]')
  });
  return it("should return the move value from the form", function() {
    var d = new display.css_display;
    domina.set_attr_BANG_.call(null, domina.by_id.call(null, "zero"), new cljs.core.Keyword(null, "checked", "checked", 1756218137), true);
    expect(abstract_display.get_move.call(null, d)).toBe("0");
    domina.set_attr_BANG_.call(null, domina.by_id.call(null, "one"), new cljs.core.Keyword(null, "checked", "checked", 1756218137), true);
    return expect(abstract_display.get_move.call(null, d)).toBe("1")
  })
});
describe("css-display end of game messages", function() {
  beforeEach(function() {
    return affix("#board")
  });
  it("should display a win message", function() {
    var d_5826 = new display.css_display;
    abstract_display.display_win_message.call(null, d_5826);
    return expect(domina.text.call(null, domina.css.sel.call(null, "#board h1"))).toMatch("Win")
  });
  it("should display a lose message", function() {
    var d_5827 = new display.css_display;
    abstract_display.display_lose_message.call(null, d_5827);
    return expect(domina.text.call(null, domina.css.sel.call(null, "#board h1"))).toMatch("Lose")
  });
  return it("should display a tie message", function() {
    var d_5828 = new display.css_display;
    abstract_display.display_tie_message.call(null, d_5828);
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
