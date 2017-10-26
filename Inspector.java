import java.lang.reflect.*;
import java.lang.reflect.Field;

public class Inspector {
	Object object =  null;
	Class classObject =null;
	public void inspect(Object obj,boolean recursive) {
		try {
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
			
			Field[] fields= classObject.getFields();
			System.out.println("Fields: ");
			for (Field f: fields) {
				System.out.println("\t<"+f.getName()+">");
				System.out.println("\ttype: "+f.getType().toString());
				System.out.println("\tmodifiers: "+f.getModifiers());
				if (f.getType().isPrimitive()) {
					System.out.println("\tvalue: "+f.get(object).toString());
				}
				else if (recursive == false ){
					System.out.println("\treference value: "+f.get(object).getClass().getName()+", "+f.get(object).getClass().hashCode());
					
				}
			}
				
		}
		catch(Exception e) {}
		
		
	}
}
