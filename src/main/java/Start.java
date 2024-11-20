import org.hinoob.apollo.ApolloBot;

public class Start {

    public static void main(String[] args) {
        ApolloBot.setInstance(new ApolloBot());
        ApolloBot.getInstance().start();
    }
}
