package event;

import cloudlet.Server;

public class CloudletCompletionEvent extends Event{

    public CloudletCompletionEvent(int type, double time) {
        super(type, time);
    }

    public static void createNewCloudletCompletionEvent(){


    }
}
