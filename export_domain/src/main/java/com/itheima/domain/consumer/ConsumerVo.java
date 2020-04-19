package com.itheima.domain.consumer;

public class ConsumerVo {
    private int success;
    private int defeat;
    private int update;

    public ConsumerVo(int success, int defeat, int update) {
        this.success = success;
        this.defeat = defeat;
        this.update = update;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getDefeat() {
        return defeat;
    }

    public void setDefeat(int defeat) {
        this.defeat = defeat;
    }

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }
}
