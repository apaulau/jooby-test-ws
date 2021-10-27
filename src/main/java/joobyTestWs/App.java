package joobyTestWs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.jooby.Jooby;
import io.jooby.json.JacksonModule;

public class App extends Jooby {
    {
        install(new JacksonModule());

        assets("/", "index.html");

        JsonMapper mapper = new JsonMapper();
        ws("/ws", ((ctx, configurer) -> {
            configurer.onMessage((ws, message) -> {
                JsonNode json = message.to(JsonNode.class);
                System.out.println("msg: " + json);
                try {
                    ws.send(mapper.writeValueAsString(json));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        }));
    }

    public static void main(final String[] args) {
        runApp(args, App::new);
    }
}
