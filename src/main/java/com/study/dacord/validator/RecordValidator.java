package com.study.dacord.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.study.dacord.model.Record;

@Component
public class RecordValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Record.class.equals(clazz);
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		Record r = (Record) obj;
		if(StringUtils.isEmpty(r.getContent())) {
			errors.rejectValue("content", "key", "내용을 입력하세요.");
		}
	}
	
}
