import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { QuikzQuestionModule } from './question/question.module';
import { QuikzQuestionLogModule } from './question-log/question-log.module';
import { QuikzCountryModule } from './country/country.module';
import { QuikzCategoryModule } from './category/category.module';
import { QuikzTopicModule } from './topic/topic.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        QuikzQuestionModule,
        QuikzQuestionLogModule,
        QuikzCountryModule,
        QuikzCategoryModule,
        QuikzTopicModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QuikzEntityModule {}
