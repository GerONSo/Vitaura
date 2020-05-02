package com.example.vitaura.prices

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class PriceSet(title: String, items: List<Price>) : ExpandableGroup<Price>(title, items)