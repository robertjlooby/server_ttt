CSSDisplay =
  resetBoard: (marker) ->
    str = ""
    for row in [0..2]
      str += "<div id=\"row#{row}\">"
      for col in [0..2]
        cell = (3 * row) + col
        str += "<div id=\"cell#{cell}\">
                  <button type=\"button\" class=\"button\">#{cell}</button>
                </div>"
      str += "</div>"
    $("#board").html(str)

  displayForm: ->
    str = "<div id=\"newGameForm\">
            <div>
              Marker:
                <input type=\"radio\" name=\"marker\" value=\"X\" checked> X
                <input type=\"radio\" name=\"marker\"   value=\"O\"> O
            </div>
            <div>
              Move:
                <input type=\"radio\" name=\"move\" value=\"0\" checked> First
                <input type=\"radio\" name=\"move\" value=\"1\"> Second
            </div>
            <button type=\"button\" id=\"newGameButton\">Play!</button>
          </div>"
    $("#board").append(str)
    $("#newGameButton")

  getCell: (cellNum) ->
    $("#cell#{cellNum}")

  getButton: (cellNum) ->
    $("#cell#{cellNum} .button")

  makeMove: (marker, cellNum) ->
    $("#cell#{cellNum}").html(marker)

  getMarker: ->
    $("input[name='marker']:checked").val()

  getMove: ->
    $("input[name='move']:checked").val()

  displayWinMessage: ->
    $("#board").prepend("<h1>You Win!</h1>")

  displayLoseMessage: ->
    $("#board").prepend("<h1>You Lose!</h1>")

  displayTieMessage: ->
    $("#board").prepend("<h1>It is a Tie!</h1>")

(exports ? this).CSSDisplay = CSSDisplay
