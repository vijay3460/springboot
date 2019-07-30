package com.medlife.deliveries.model.data;

public enum Partners {
    CLICKPOST(1L), DELHIVERY(2L);

    private Long id;

    Partners(Long id)
    {
        this.id = id;
    }

    public Long id(){
        return this.id;
    }
}
