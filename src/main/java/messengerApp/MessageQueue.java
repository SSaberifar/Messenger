package messengerApp;

import java.util.LinkedList;
import java.util.Queue;

public class MessageQueue<T> {

        private LinkedList<T> queue = new LinkedList<>();

        public void addMessage(T message){
            queue.add(message);
        }

        public T getMessage(){
            return queue.poll();
        }

        public boolean isEmpty(){
            return queue.isEmpty();
        }

        public void clear() {
            queue.clear();
        }
}
