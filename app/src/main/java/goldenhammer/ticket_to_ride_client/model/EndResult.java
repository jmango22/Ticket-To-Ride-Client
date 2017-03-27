package goldenhammer.ticket_to_ride_client.model;

/**
 * Created by McKean on 3/25/2017.
 */

public class EndResult {
    private int player;
    private int builtTrainPoints;
    private int completedDestinations;
    private int incompleteDestinations;
    private int longestContinuousTrain;
    private int total;

    public EndResult(int player, int builtTrainPoints, int completedDestinations, int incompleteDestinations,
                     int longestContinuousTrain, int total){
        this.player = player;
        this. builtTrainPoints = builtTrainPoints;
        this.completedDestinations = completedDestinations;
        this.incompleteDestinations = incompleteDestinations;
        this.longestContinuousTrain = longestContinuousTrain;
        this. total= total;
    }

    public EndResult(){}

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getBuiltTrainPoints() {
        return builtTrainPoints;
    }

    public void setBuiltTrainPoints(int builtTrainPoints) {
        this.builtTrainPoints = builtTrainPoints;
    }

    public int getCompletedDestinations() {
        return completedDestinations;
    }

    public void setCompletedDestinations(int completedDestinations) {
        this.completedDestinations = completedDestinations;
    }

    public int getIncompleteDestinations() {
        return incompleteDestinations;
    }

    public void setIncompleteDestinations(int incompleteDestinations) {
        this.incompleteDestinations = incompleteDestinations;
    }

    public int getLongestContinuousTrain() {
        return longestContinuousTrain;
    }

    public void setLongestContinuousTrain(int longestContinuousTrain) {
        this.longestContinuousTrain = longestContinuousTrain;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
