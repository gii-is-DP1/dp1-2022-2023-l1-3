<%@ attribute name="board" required="false" rtexprvalue="true" type="org.springframework.samples.xtreme.board.ParchisBoard" description="board to be rendered" %>
<canvas id="canvas" width="${board.width}" height="${board.height}"></canvas>
<img id="source" src="${board.background}" style="display:none">

<script>
function drawBoard(){ 
    var canvas = document.getElementById("canvas");
    var ctx = canvas.getContext("2d");
    var image = document.getElementById('source');
    ctx.drawImage(image, 0, 0, canvas.width, canvas.height);     
    <jsp:doBody/>
}
window.onload =drawBoard();
</script>
