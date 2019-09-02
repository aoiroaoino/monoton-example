import com.google.inject.AbstractModule
import example.MyRouter
import monoton.server.Router

class AppModule extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[Router]).to(classOf[MyRouter])
  }
}
