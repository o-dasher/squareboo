package com.dasher.squareboo.framework.assets;


public interface IThrowsClassNotInInstance {
    void throwClassNotFoundInThis(Class<?> klass) throws ClassNotFoundException;
}
