package com.NMolina.to_do_list_TT.domain.port.in.status;

import java.util.List;

import com.NMolina.to_do_list_TT.domain.model.Status;

public interface ListStatusesUseCase {

    List<Status> listAll();
}
