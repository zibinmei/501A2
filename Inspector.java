import java.lang.reflect.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;


public class Inspector {
	private List<Class> inspected = new ArrayList<>();
	private List<Object> Queue = new ArrayList<>();

	private Object object =  null;
	private Class classObject =null;
	
	public void inspect(Object obj,boolean recursive) throws Exception, IllegalArgumentException, IllegalAccessException {
		object = obj;
		classObject= obj.getClass();
		inspected.add(classObject);
		
		System.out.println("Name of declaring class: "+classObject.getName());
		System.out.println("Name of immediate super class: "+ classObject.getSuperclass().getName());
		
		//print out all interfaces
		Class[] interfaces =classObject.getInterfaces();
		System.out.println("Name of interface the class implemented: ");
		for (Class i : interfaces) {
			System.out.println("\t"+i.getName());	
		}
		
		//print out all method
		Method[] methods = classObject.getDeclaredMethods();
		System.out.println("Method: ");
		for (Method m : methods) {
			System.out.println("\t<"+m.getName()+">");
			System.out.print("\t\texception thrown: ");
			arrayprinter(m.getExceptionTypes());
			System.out.print("\t\tparameter types: ");
			arrayprinter(m.getParameterTypes());
			System.out.println("\t\treturn type: "+m.getReturnType().getSimpleName());
			System.out.println("\t\tmodifiers: "+Modifier.toString(m.getModifiers()));
		}
		
		//print out all constructor
		Constructor[] construtors= classObject.getConstructors();
		System.out.println("Constructors: ");
		for (Constructor c : construtors) {
			System.out.println("\t<"+c.getName()+">");
			System.out.print("\t\tparameter types: ");
			arrayprinter(c.getParameterTypes());
			System.out.println("\t\tmodifiers: "+Modifier.toString(c.getModifiers()));
			
		}
		getFields();

		
		
		inspected.add(classObject);
		//run inspect on the queue
		if (Queue.isEmpty() == false) {
			Object o= Queue.get(0);
			Queue.remove(0);
			if(inspected.contains(o.getClass()) == false) {
				System.out.println("######################################");
				inspect(o,recursive);
			}
		}
			
	
		
	}
	
	private void getFields() {
		//print out all Fields
		Field[] fields= classObject.getDeclaredFields();
		
		System.out.println("Fields: ");
		for (Field f: fields) {
			f.setAccessible(true);
			System.out.println("\t<"+f.getName()+">");
			System.out.println("\t\ttype: "+f.getType().getSimpleName());
			System.out.println("\t\tmodifiers: "+Modifier.toString(f.getModifiers()));
			if (f.get(object) != null) {
				if (f.getType().isArray()) {

					System.out.println("\t\tlength: "+Array.getLength(f.get(object)));
					System.out.println("\t\tvalue: "+f.get(object).toString());
					//reference value 
					if (recursive && f.get(obj).getClass().getComponentType().isPrimitive()==false)
						Queue.add(f.get(object));
					else 
						System.out.println("\t\treference value: "+f.get(object).getClass().getSimpleName()+", "+f.get(object).getClass().hashCode());
			
//							display_fieldArray(f.get(object),f.getType());
				}
				else {
					System.out.println("\t\tvalue: "+f.get(object).toString());
					//reference value 
					if (recursive && f.getType().isPrimitive() ==false)
						Queue.add(f.get(object));
					else 
						System.out.println("\t\treference value: "+f.get(object).getClass().getSimpleName()+", "+f.get(object).getClass().hashCode());
			
				}
			}
			else {
				System.out.println("\t\tvalue: NULL");
			}
	}
	

	private void display_fieldArray(Object arr) {
		int current_index =0;
		Class typeofarr = arr.getClass().getComponentType();
		int length= Array.getLength(arr);
		
//		Object[] array =  Array.newInstance(typeofarr, length) ;
		
		for (int i =0; i <array.length; i++ ) {
		
			System.out.print(array[i]);

			if (current_index != (array.length-1) ) {
				System.out.print(",");
			}
			else;
			current_index++;
		}
		System.out.println(")");
	}
	
	private void arrayprinter(Class[] array) {
		int current_index =0;
		System.out.print("(");
		for (Class e: array) {

			System.out.print(e.getSimpleName());

			if (current_index != (array.length-1) ) {
				System.out.print(",");
			}
				
			else;
			current_index++;
		}
		System.out.println(")");
	}
	
	
}
