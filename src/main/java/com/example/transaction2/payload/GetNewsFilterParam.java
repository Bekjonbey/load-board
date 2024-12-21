package com.example.transaction2.payload;

import com.example.transaction2.entity.LocationType;
import com.example.transaction2.entity.PageEnum;
import lombok.Data;

@Data
public class GetNewsFilterParam {
    private PageEnum page;
    private LocationType locationType;
}
