package com.historiasdeusuario.Utils;

import rx.Observable;

public class ValidationUtil
{
    public static Boolean stringValidation(String... fieldList)
    {
        final boolean[] valid = {false};
        Observable.from(fieldList).all(field -> field!=null && !field.equals("")).filter(passIsTrue -> passIsTrue).subscribe(result ->  valid[0] =result );
        return valid[0];
    }
}
