package Netflix;

import org.testng.annotations.Factory;

public class NetflixFactory {
    @Factory
    public Object[] factoryMethod(){
        return new Object[]{
                new Prueba_Netflix(),
                new Prueba_Netflix(),
        };
    }
}
