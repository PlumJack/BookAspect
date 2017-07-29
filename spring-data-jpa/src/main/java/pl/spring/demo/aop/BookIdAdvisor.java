package pl.spring.demo.aop;


import org.springframework.aop.MethodBeforeAdvice;
import pl.spring.demo.annotation.NullableId;
import pl.spring.demo.annotation.SettableId;
import pl.spring.demo.common.Sequence;
import pl.spring.demo.dao.impl.BookDaoImpl;
import pl.spring.demo.exception.BookNotNullIdException;
import pl.spring.demo.to.BookTo;
import pl.spring.demo.to.IdAware;

import java.lang.reflect.Method;

public class BookIdAdvisor implements MethodBeforeAdvice {

	private Sequence sequence;
	
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {

        if (hasAnnotation(method, o, SettableId.class)) {
            setId(o, objects[0]);
        }
    }

    private void setId(Object o, Object b) {	
    	if(b instanceof IdAware && ((IdAware) b).getId() == null){
    		BookDaoImpl implementation = (BookDaoImpl) o;
    		((BookTo) b).setId(sequence.nextValue(implementation.findAll()));
    	}        
    }

    private boolean hasAnnotation (Method method, Object o, Class annotationClazz) throws NoSuchMethodException {
        boolean hasAnnotation = method.getAnnotation(annotationClazz) != null;

        if (!hasAnnotation && o != null) {
            hasAnnotation = o.getClass().getMethod(method.getName(), method.getParameterTypes()).getAnnotation(annotationClazz) != null;
        }
        return hasAnnotation;
    }
    
    public void setSequence(Sequence sequence){
    	this.sequence = sequence;
    }
}
