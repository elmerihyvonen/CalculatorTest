package fi.utu.tech;

import net.jqwik.api.*;
import org.junit.jupiter.api.Test;
import java.util.Random;

public class CalculatorTest {

	Random rnd;

	@Provide
	Arbitrary<ComplexNumber> complexNumbers(){

		Arbitrary<Double> real = Arbitraries.doubles().between(-10, 10).filter(Num -> Num != 0);
		Arbitrary<Double> img = Arbitraries.doubles().between(-10, 10).filter(Num -> Num != 0);
		return Combinators.combine(real, img).as(ComplexNumber::new);
	}
	
	// Pettämätön logiikkani:
	// ohjelma toimii jos syöte != null ja lopputulos != null
	//
	// t. 1. vuoden opiskelija
	@Property
	boolean toimii() {
		return new CalculatorApp().calculate("1 2 3") != null;
	}

	@Test
	void addTest(){
		ComplexNumber c1 = new ComplexNumber(1, -1);
		ComplexNumber c2 = new ComplexNumber(2, -2);
		ComplexNumber c3 = c1.add(c2);
	}


	//---Testataaan loppuehtoja eli, että laskut tosiaan menee oikein.---

	@Property
	boolean addPropertyTest(@ForAll("complexNumbers")  ComplexNumber c1, @ForAll("complexNumbers")  ComplexNumber c2){
		ComplexNumber c3 = c1.add(c2);
        double real_ = c1.getReal() + c2.getReal();
        double img_ = c1.getImg() + c2.getImg();
        return (c3.getReal() == real_) && (c3.getImg() == img_);
	}

	@Property
	boolean substractPropertyTest(@ForAll("complexNumbers")  ComplexNumber c1, @ForAll("complexNumbers") ComplexNumber c2){
		ComplexNumber c3 = c1.substract(c2);
        double real_ = c1.getReal() - c2.getReal();
        double img_ = c1.getImg() - c2.getImg();
        return (c3.getReal() == real_) && (c3.getImg() == img_);
	}

	@Property
	boolean multiplyPropertyTest(@ForAll("complexNumbers") ComplexNumber c1, @ForAll("complexNumbers") ComplexNumber c2){
		ComplexNumber c3 = c1.multiply(c2);
        double real_ = c1.getReal() * c2.getReal() - c1.getImg() * c2.getImg();
        double img_ = c1.getReal() * c2.getImg() + c1.getImg() * c2.getReal();
        return (c3.getReal() == real_) && (c3.getImg() == img_);
	}

	@Property
	boolean dividePropertyTest(@ForAll("complexNumbers") ComplexNumber c1, @ForAll("complexNumbers") ComplexNumber c2){
		ComplexNumber c3 = c1.divide(c2);
		double real_ = (c1.getReal() * c2.getReal() + c1.getImg() * c2.getImg())/(c2.getReal() * c2.getReal() + c2.getImg() * c2.getImg());
		double img_ = (c2.getReal() * c1.getImg() - c1.getReal() * c2.getImg())/(c2.getReal() * c2.getReal() + c2.getImg() * c2.getImg());
		return (c3.getReal() == real_) && (c3.getImg() == img_);
	}




}