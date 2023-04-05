public class BallFallBlue implements BallFall{
    /**
     * This ensures blue ball is reincarnated if it hasn't been reset before, it won't if it will collide with a ball already on the board
     */
    public void think(Ball ball){
        if(ball.getResetCount() >= 2){
            return;
        }
        else{
            ball.reset();
            ball.setResetCount(ball.getResetCount()+1);
            if(Table.resetCheck(ball)){
                ball.setPocketed(true);
            }
        }
    }
}