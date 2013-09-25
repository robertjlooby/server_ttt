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
    CSSDisplay.resetBoard.call(null, function(p1__30054_SHARP_) {
      return[cljs.core.str(p1__30054_SHARP_)].join("")
    });
    expect(domina.html.call(null, domina.by_id.call(null, "board"))).toMatch('class\x3d"button"\x3e0\x3c');
    expect(domina.html.call(null, domina.by_id.call(null, "board"))).toMatch('\x3cdiv id\x3d"row2"\x3e');
    return expect(domina.html.call(null, domina.by_id.call(null, "board"))).toMatch('\x3cdiv id\x3d"cell8"\x3e')
  });
  it("should remove form", function() {
    expect(domina.text.call(null, domina.by_id.call(null, "newGameForm"))).toBe("a form");
    CSSDisplay.resetBoard.call(null, function(p1__30055_SHARP_) {
      return[cljs.core.str(p1__30055_SHARP_)].join("")
    });
    return expect(domina.by_id.call(null, "newGameForm")).toBe(null)
  });
  return it("should attach the given event handler to each button", function() {
    var fun = jasmine.createSpy("fun");
    CSSDisplay.resetBoard.call(null, fun);
    cljs.core.doall.call(null, function() {
      var iter__3530__auto__ = function iter__30056(s__30057) {
        return new cljs.core.LazySeq(null, false, function() {
          var s__30057__$1 = s__30057;
          while(true) {
            var temp__4092__auto__ = cljs.core.seq.call(null, s__30057__$1);
            if(temp__4092__auto__) {
              var s__30057__$2 = temp__4092__auto__;
              if(cljs.core.chunked_seq_QMARK_.call(null, s__30057__$2)) {
                var c__3528__auto__ = cljs.core.chunk_first.call(null, s__30057__$2);
                var size__3529__auto__ = cljs.core.count.call(null, c__3528__auto__);
                var b__30059 = cljs.core.chunk_buffer.call(null, size__3529__auto__);
                if(function() {
                  var i__30058 = 0;
                  while(true) {
                    if(i__30058 < size__3529__auto__) {
                      var c = cljs.core._nth.call(null, c__3528__auto__, i__30058);
                      cljs.core.chunk_append.call(null, b__30059, domina.events.dispatch_BANG_.call(null, domina.by_id.call(null, [cljs.core.str("cell"), cljs.core.str(c)].join("")), new cljs.core.Keyword(null, "click", "click", 1108654330), cljs.core.PersistentArrayMap.EMPTY));
                      var G__30064 = i__30058 + 1;
                      i__30058 = G__30064;
                      continue
                    }else {
                      return true
                    }
                    break
                  }
                }()) {
                  return cljs.core.chunk_cons.call(null, cljs.core.chunk.call(null, b__30059), iter__30056.call(null, cljs.core.chunk_rest.call(null, s__30057__$2)))
                }else {
                  return cljs.core.chunk_cons.call(null, cljs.core.chunk.call(null, b__30059), null)
                }
              }else {
                var c = cljs.core.first.call(null, s__30057__$2);
                return cljs.core.cons.call(null, domina.events.dispatch_BANG_.call(null, domina.by_id.call(null, [cljs.core.str("cell"), cljs.core.str(c)].join("")), new cljs.core.Keyword(null, "click", "click", 1108654330), cljs.core.PersistentArrayMap.EMPTY), iter__30056.call(null, cljs.core.rest.call(null, s__30057__$2)))
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
      var iter__3530__auto__ = function iter__30060(s__30061) {
        return new cljs.core.LazySeq(null, false, function() {
          var s__30061__$1 = s__30061;
          while(true) {
            var temp__4092__auto__ = cljs.core.seq.call(null, s__30061__$1);
            if(temp__4092__auto__) {
              var s__30061__$2 = temp__4092__auto__;
              if(cljs.core.chunked_seq_QMARK_.call(null, s__30061__$2)) {
                var c__3528__auto__ = cljs.core.chunk_first.call(null, s__30061__$2);
                var size__3529__auto__ = cljs.core.count.call(null, c__3528__auto__);
                var b__30063 = cljs.core.chunk_buffer.call(null, size__3529__auto__);
                if(function() {
                  var i__30062 = 0;
                  while(true) {
                    if(i__30062 < size__3529__auto__) {
                      var c = cljs.core._nth.call(null, c__3528__auto__, i__30062);
                      cljs.core.chunk_append.call(null, b__30063, expect(fun).toHaveBeenCalledWith(c));
                      var G__30065 = i__30062 + 1;
                      i__30062 = G__30065;
                      continue
                    }else {
                      return true
                    }
                    break
                  }
                }()) {
                  return cljs.core.chunk_cons.call(null, cljs.core.chunk.call(null, b__30063), iter__30060.call(null, cljs.core.chunk_rest.call(null, s__30061__$2)))
                }else {
                  return cljs.core.chunk_cons.call(null, cljs.core.chunk.call(null, b__30063), null)
                }
              }else {
                var c = cljs.core.first.call(null, s__30061__$2);
                return cljs.core.cons.call(null, expect(fun).toHaveBeenCalledWith(c), iter__30060.call(null, cljs.core.rest.call(null, s__30061__$2)))
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
