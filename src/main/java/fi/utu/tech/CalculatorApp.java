package fi.utu.tech;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Ohjelma joka laskee 2. asteen yhtälön ratkaisun
 * tai ratkaisut, graafisesti.
 * 
 * Käytin tässä lukiossa opittua ratkaisukaavaa:
 * https://fi.wikipedia.org/wiki/Toisen_asteen_yht%C3%A4l%C3%B6#Toisen_asteen_yht%C3%A4l%C3%B6n_ratkaisukaava
 * 
 * @author 1. vuoden opiskelija
 */
public class CalculatorApp extends Application {
	
	// ensimmäinen Java-yritelmäni
	// joskus toimii, joskus ei
	//
	// t. 1. vuoden opiskelija
	
	String calculate(String input) {
		String[] i = input.split(" ");
		
		double a = Double.parseDouble(i[0]);
		double b = Double.parseDouble(i[1]);
		double c = Double.parseDouble(i[2]);

		double r1, r2;
		double determinant = b*b - 4*a*c;
		double realPart = -b / (2*a);

		if(determinant >= 0){

			r1 = (-b+Math.sqrt(determinant))/(2*a);
			r2 = (-b-Math.sqrt(determinant))/(2*a);

			if (r1 == r2)
				return "Ratkaisu on: x = " + r1;
			else
				return "Ratkaisut ovat: x = " + r1 + " ja x = " + r2;
		}
		else{
			ComplexNumber complexNumber1 = new ComplexNumber(realPart, Math.sqrt(-determinant)/(2*a));
			ComplexNumber complexNumber2 = new ComplexNumber(realPart, -Math.sqrt(-determinant)/(2*a));
			return "Ratkaisut ovat: x = " + complexNumber1.printResult() + " ja x = " + complexNumber2.printResult();
		}
	}
	
	// tämä vaikuttaa valmiilta
	// tätä ei tarvitse muuttaa
	@Override
	public void start(Stage window) {
		window.setTitle("2. asteen yhtälön ratkaisin");

		VBox root = new VBox();
		root.setSpacing(12);
		root.getChildren().add(new Label("Ratkaistaan yhtälö ax^2 + bx + c."));
		root.getChildren().add(new Label("Syötä alle muodossa: a väli b väli c"));

		TextField input = new TextField();
		root.getChildren().add(input);

		Button btn = new Button("Laske");
		root.getChildren().add(btn);
		
		root.getChildren().add(new Label("Tulos:"));
		
		Label output = new Label("");
		root.getChildren().add(output);

		btn.setOnAction(e -> output.setText(calculate(input.getText())));
		
		window.setScene(new Scene(root, 400, 300));
		window.show();
	}
}
class ComplexNumber {

    private double real;
    private double img;

    //---Luokkainvariantti---
    /**
     * @.classInvariant getReal() != null &
     * 					getImg() != null
     */

    //---Muodostimet---
    /**
     * @.pre true
     * @.post getReal().equals(0) &
     * 		  getImg().equals(0) */
    public ComplexNumber(){
        this.img = 0;
        this.real = 0;
    }
    /**
     * @.pre real_ != null & img_ != null
     * @.post getReal().equals(real_) &
     * 		  getImg().equals(img_) */
    public ComplexNumber(double real_, double img_){
        this.real = real_;
        this.img = img_;
    }

    //---Havainnointioperaatiot
    /**
     * @.pre true
     * @.post RESULT == (real) */
    double getReal(){ return this.real; }
    /**
     * @.pre true
     * @.post RESULT == (img) */
    double getImg(){ return this.img; }

    //---Muunnosoperaatiot---
    /**
     * @.pre r != null
     * @.post this.getReal().equals(r); */
    void setReal(double r){
        this.real = r;
    }
    /**
     *@.pre i != null
     *@.post this.getImg().equals(i); */
    void setImg(double i){
        this.img = i;
    }

    /**
     * @.pre c != null
     * @.post RESULT == (this + c) &
     *        RESULT.real == this.real + c.real &
     *        RESULT.img == this.img + c.img */
    ComplexNumber add(ComplexNumber c){
        ComplexNumber temp = new ComplexNumber();
        temp.real = this.real + c.real;
        temp.img = this.img + c.img;
        return temp;
    }
    /**
     * @.pre c != null
     * @.post RESULT == (this - c) &
     *        RESULT.real == this.real - c.real &
     *        RESULT.img == this.img -c.img */
    ComplexNumber substract(ComplexNumber c){
        ComplexNumber temp = new ComplexNumber();
        temp.real = this.real - c.real;
        temp.img = this.img - c.img;
        return temp;
    }
    /**
     * @.pre c != null
     * @.post RESULT == (this * c) &
     *        RESULT.real == this.real * c.real - this.img * c.img &
     *        RESULT.img == this.real * c.img + this.img * c.real */
    ComplexNumber multiply(ComplexNumber c){
        ComplexNumber temp = new ComplexNumber();
        temp.real = this.real * c.real - this.img * c.img;
        temp.img = this.real * c.img + this.img * c.real;
        return temp;
    }
    /**
     * @.pre c != null
     * @.post RESULT == (this / c) &
     *        RESULT.real = (this.real*c.real + this.img*c.img)/(c.real*c.real + c.img*c.img) &
     *        RESULT.img = (c.real*this.img - this.real*c.img)/(c.real*c.real + c.img*c.img) */
    ComplexNumber divide(ComplexNumber c){
        ComplexNumber temp = new ComplexNumber();
        temp.setReal((this.real*c.real + this.img*c.img) / (c.real*c.real + c.img*c.img));
        temp.setImg((c.real*this.img - this.real*c.img) / (c.real*c.real + c.img*c.img));
        return temp;
    }
    /**
     * @.pre true
     * @.post RESULT == (real + " + " + img + "i") */
    String printResult(){
        return this.real + " + " + this.img + "i";
    }

}

