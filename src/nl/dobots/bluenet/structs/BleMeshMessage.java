package nl.dobots.bluenet.structs;

import junit.framework.Assert;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import nl.dobots.bluenet.BleTypes;

/**
 * Created by dominik on 15-7-15.
 */
public class BleMeshMessage {

    // size of message without payload is:
    // 1B channel + 1B RESERVED + 2B length + 6B target + 2B type
    private static final int SIZE_WITHOUT_PAYLOAD = 12;

    private int channel;
    private int length;
    private byte[] target;
    private int type;
    private byte[] payload;

    public BleMeshMessage(int channel, byte[] target, int type, int length, byte[] payload) {
        Assert.assertTrue("target has to have length 6", target.length == 6);

        this.channel = channel;
        this.length = length;
        this.target = target;
        this.type = type;
        this.payload = payload;
    }

    public BleMeshMessage(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        channel = bb.get();
        bb.get(); // skip reserved field
        length = bb.getShort();
        target = new byte[6];
        bb.get(target);
        type = bb.getShort();
        payload = new byte[bb.remaining()];
        bb.get(payload);
    }

    public byte[] toArray() {
        ByteBuffer bb = ByteBuffer.allocate(SIZE_WITHOUT_PAYLOAD + payload.length);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        bb.put((byte) channel);
        bb.put((byte) BleTypes.RESERVED);
        bb.putShort((short) length);
        bb.put(target);
        bb.putShort((short) type);
        bb.put(payload);

        return bb.array();
    }

    @Override
    public String toString() {
        return String.format("{channel: %d, length: %d, target: %s, type: %s, payload: %s}",
                channel, length, Arrays.toString(target), type, Arrays.toString(payload));
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public byte[] getTarget() {
        return target;
    }

    public void setTarget(byte[] target) {
        this.target = target;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

}
