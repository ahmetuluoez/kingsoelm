import logic.CalculatorController;
import model.CalculatorModel;
import view.CalculatorView;

public class Main {
    public static void main(String[] args) {
        CalculatorModel model = new CalculatorModel();
        CalculatorView view = new CalculatorView();
        new CalculatorController(model, view);
        view.setVisible(true);
    }
}
