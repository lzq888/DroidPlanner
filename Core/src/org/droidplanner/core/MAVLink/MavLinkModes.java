package org.droidplanner.core.MAVLink;

import org.droidplanner.core.model.Drone;

import com.MAVLink.Messages.ApmModes;
import com.MAVLink.Messages.ardupilotmega.msg_set_mode;
import com.MAVLink.Messages.ardupilotmega.msg_set_position_target_global_int;
import com.MAVLink.Messages.enums.MAV_FRAME;

public class MavLinkModes {

	private static final int MAVLINK_SET_POS_TYPE_MASK_POS_IGNORE = ((1 << 0) | (1 << 1) | (1 << 2));
	private static final int MAVLINK_SET_POS_TYPE_MASK_VEL_IGNORE = ((1 << 3) | (1 << 4) | (1 << 5));
	private static final int MAVLINK_SET_POS_TYPE_MASK_ACC_IGNORE = ((1 << 6) | (1 << 7) | (1 << 8));

	public static void sendGuidedPosition(Drone drone, double latitude, double longitude, double alt) {
		msg_set_position_target_global_int msg = new msg_set_position_target_global_int();
		msg.type_mask =MAVLINK_SET_POS_TYPE_MASK_ACC_IGNORE | MAVLINK_SET_POS_TYPE_MASK_VEL_IGNORE;
		msg.coordinate_frame = MAV_FRAME.MAV_FRAME_GLOBAL_RELATIVE_ALT_INT;
		msg.lat_int = (int) (latitude * 1E7);
		msg.lon_int = (int) (longitude * 1E7);
		msg.alt = (float) alt;
		msg.target_system = 1;
		msg.target_component = 1;
		drone.getMavClient().sendMavPacket(msg.pack());
	}

	public static void sendGuidedVelocity(Drone drone, double xVel, double yVel, double zVel) {
		msg_set_position_target_global_int msg = new msg_set_position_target_global_int();
		msg.type_mask =MAVLINK_SET_POS_TYPE_MASK_ACC_IGNORE | MAVLINK_SET_POS_TYPE_MASK_POS_IGNORE;
		msg.coordinate_frame = MAV_FRAME.MAV_FRAME_GLOBAL_RELATIVE_ALT_INT;
		msg.vx = (float) xVel;
		msg.vy = (float) yVel;
		msg.vx = (float) zVel;
		msg.target_system = 1;
		msg.target_component = 1;
		drone.getMavClient().sendMavPacket(msg.pack());
	}

	public static void sendGuidedPositionAndVelocity(Drone drone, double latitude, double longitude, double alt,  double xVel, double yVel, double zVel) {
		msg_set_position_target_global_int msg = new msg_set_position_target_global_int();
		msg.type_mask =MAVLINK_SET_POS_TYPE_MASK_ACC_IGNORE;
		msg.coordinate_frame = MAV_FRAME.MAV_FRAME_GLOBAL_RELATIVE_ALT_INT;
		msg.lat_int = (int) (latitude * 1E7);
		msg.lon_int = (int) (longitude * 1E7);
		msg.alt = (float) alt;
		msg.vx = (float) xVel;
		msg.vy = (float) yVel;
		msg.vx = (float) zVel;
		msg.target_system = 1;
		msg.target_component = 1;
		drone.getMavClient().sendMavPacket(msg.pack());
	}
	
	public static void changeFlightMode(Drone drone, ApmModes mode) {
		msg_set_mode msg = new msg_set_mode();
		msg.target_system = 1;
		msg.base_mode = 1; // TODO use meaningful constant
		msg.custom_mode = mode.getNumber();
		drone.getMavClient().sendMavPacket(msg.pack());
	}
}
