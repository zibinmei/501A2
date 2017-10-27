import java.lang.reflect.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;


public class Inspector {
	private List<Class> inspected = new ArrayList<>();
	private List<Object> Queue = new ArrayList<>();
	static final boolean CHECK_PRIMITIVE =false;
	private Object object =  null;
	private Class classObject =null;
	
	public void inspect(Object obj,boolean recursive) throws Exception, IllegalArgumentException, IllegalAccessException {
		object = obj;
		classObject= obj.getClass();
		inspected.add(classObject);
		
		System.out.println("Name of declaring class: "+classObject.getName());
		System.out.println("Name of immediate super class: "+ classObject.getSuperclass().getName());
		getAllInterfaces();
		getAllMethods();
		getAllConstructor();
		getAllFields(recursive);
		
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
	
	//print out all interfaces
	private void getAllInterfaces() {
		Class[] interfaces =classObject.getInterfaces();
		System.out.println("Name of interface the class implemented: ");
		for (Class i : interfaces) {
			System.out.println("\t"+i.getName());	
		}
	}
	
	
	//print out all method
	private void getAllMethods() {
		Method[] methods = classObject.getDeclaredMethods();
		System.out.println("Method: ");
		for (Method m : methods) {
			System.out.println("\t<"+m.getName()+">");
			System.out.print("\t\texception thrown: ");
			return_handler(m.getExceptionTypes());
			System.out.print("\t\tparameter types: ");
			return_handler(m.getParameterTypes());
			System.out.println("\t\treturn type: "+m.getReturnType().getSimpleName());
			System.out.println("\t\tmodifiers: "+Modifier.toString(m.getModifiers()));
		}
	}
	
	//print out all constructor
	private void getAllConstructor() {
		Constructor[] construtors= classObject.getConstructors();
		System.out.println("Constructors: ");
		for (Constructor c : construtors) {
			System.out.println("\t<"+c.getName()+">");
			System.out.print("\t\tparameter types: ");
			return_handler(c.getParameterTypes());
			System.out.println("\t\tmodifiers: "+Modifier.toString(c.getModifiers()));
			
		}
	}
	//print out all Fields
	private void getAllFields(boolean recursive) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields= classObject.getDeclaredFields();
		System.out.println("Fields: ");
		for (Field f: fields) {
			f.setAccessible(true);
			System.out.println("\t<"+f.getName()+">");
			System.out.println("\t\ttype: "+f.getType().getSimpleName());
			System.out.println("\t\tmodifiers: "+Modifier.toString(f.getModifiers()));
			//field values 
			if (f.get(object) != null) {
				//print out array info
				if (f.getType().isArray()) {
					System.out.println("\t\tlength: "+Array.getLength(f.get(object)));
					System.out.print("\t\tvalue: ");
					display_fieldArray(f.get(object));
					//reference value && recursive
					if (recursive)
						Queue.add(f.get(object));					
					else 
						System.out.println("\t\treference value: "+f.get(object).getClass().getSimpleName()+", "+f.get(object).getClass().hashCode());
			
				}
				
				//print out info for non array item
				else {
					System.out.println("\t\tvalue: "+f.get(object).toString());
					//reference value && recursive
					if (recursive)
						recursiveHelper(f.get(object));
					else
						System.out.println("\t\treference value: "+f.get(object).getClass().getSimpleName()+", "+f.get(object).getClass().hashCode());
			
				}
			}
			else {
				System.out.println("\t\tvalue: NULL");
			}
		}
	}
	//use to determine how to interface with different object type, esp. array and non-array
	private void recursiveHelper(Object obj) {
		if (obj != null) {
			if(obj.getClass().isPrimitive())
				Queue.add(object);
			else if(obj.getClass().isArray()) {
				if (Array.get(obj, 0)!=null && obj.getClass().getComponentType().isPrimitive() == CHECK_PRIMITIVE)
					Queue.add(Array.get(obj,0));
				else;
				
			}
		}
			
		else;
	}
	//displaying array contents
	private void display_fieldArray(Object arr) {
		int current_index =0;
		Class typeofarr = arr.getClass().getComponentType();
		int length= Array.getLength(arr);
		System.out.print("{");
		for (int i =0; i <length; i++ ) {
		
			System.out.print(Array.get(arr,i));

			if (current_index != (length-1) ) {
				System.out.print(",");
			}
			else;
			current_index++;
		}
		System.out.println("}");
	}
	//handler the returned array of getmethods/constructors/interfaces
	private void return_handler(Class[] array) {
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
