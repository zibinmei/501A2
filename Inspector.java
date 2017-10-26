import java.lang.reflect.*;

public class Inspector {
	Object object =  null;
	Class classObject =null;
	public void inspect(Object obj,boolean recursive) {
		object = obj;
		classObject= obj.getClass();
		
		
		System.out.println("Name of declaring class: "+classObject.getDeclaringClass().getName());
		System.out.print("Name of immediate super class: "+ classObject.getSuperclass().getName());
		
		Class[] interfaces =classObject.getInterfaces();
		System.out.println("Name of interface the class inplemented: ");
		for (Class i : interfaces) {
			System.out.println("\t"+i.getName());	
		}
		
		Method[] methods = classObject.getDeclaredMethods();
		System.out.println("Method: ");
		for (Method m : methods) {
			System.out.println("\t<"+m.getName()+">");
			System.out.println("\texception thrown: "+m.getExceptionTypes().toString());
			System.out.println("\tparameter types: "+m.getParameterTypes().toString());
			System.out.println("\treturn type: "+m.getReturnType().toString());
			System.out.println("\tmodifiers: "+m.getModifiers());
		}
		
		Constructor[] construtors= classObject.getConstructors();
		System.out.println("Constructors: ");
		for (Constructor c : construtors) {
			System.out.println("\t<"+c.getName()+">");
			System.out.println("\tparameter types: "+c.getParameterTypes().toString());
			System.out.println("\tmodifiers: "+c.getModifiers());
			
		}
		
		
	}
}
