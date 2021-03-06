/*
* Copyright (c) Joan-Manuel Marques 2013. All rights reserved.
* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
*
* This file is part of the practical assignment of Distributed Systems course.
*
* This code is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This code is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this code.  If not, see <http://www.gnu.org/licenses/>.
*/

package recipesService.raftRPC;

import java.io.Serializable;

/**
 * 
 * Response message to a RequestVoteRPC
 * 
 * @author Joan-Manuel Marques
 * May 2013
 *
 */

public class RequestVoteResponse extends RaftResponse implements Serializable{
	
	private static final long serialVersionUID = 5958926518166419833L;

	private boolean voteGranted;// true means candidate received vote
	
	/**
	 * RequestVoteResponse
	 * @param term
	 * @param voteGranted
	 */
	public RequestVoteResponse (long term, boolean voteGranted){
		super(term); // currentTerm, for candidate to update itself
		this.voteGranted = voteGranted;
	}

	public boolean isVoteGranted() {
		return voteGranted;
	}
	
	@Override
	public RaftResponseType type() {
		return RaftResponseType.REQUEST_VOTE;
	}

	@Override
	public String toString() {
		return "RequestVoteResponse [getTerm()=" + getTerm()
				+ ", voteGranted=" + voteGranted + "]";
	}
}
